package code;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Main {
	public static int pb = 0;
	public static int nop = 0,noc = 0,calls = 0,fanout = 0,nom = 0,loc = 0,cyclo = 0;
	private static double andc = 0, ahh = 0;
	private static HashMap<String, ArrayList<String>> map = new HashMap<>();
	private static ArrayList<String> rootClasses = new ArrayList<>();

	public static void main(final String[] args) {
		if(args.length == 0)System.out.println("Syntax Error");
		else if(args[0].equals("-p"))pb = 1;
		else if(args[0].equals("-b"))pb = 2;
		else System.err.println("Choose option -p or -b");

		if(pb == 1) {
			for(int i = 1; i < args.length; i++){
				String p = args[i];
				int index = p.lastIndexOf('/') + 1;
				String packageName = p.substring(index);
				File newfile = new File(p);
				if(newfile.isDirectory()) {
					String d = p;
					while(new File(d).isDirectory() && d.length() > 0) {
						d = d.substring(0, d.length()-1);
					}
					while(!new File(d).isDirectory() && d.length() > 0) {
						d = d.substring(0, d.length()-1);
					}
					openDir(newfile, d);

					//andc
					int total1 = 0;
					for(Map.Entry<String, ArrayList<String>> entry: map.entrySet()){
						total1 += entry.getValue().size();
					}
					andc = (double)total1 / map.size();

					//ahh
					int total2 = 0;
					for(int j = 0; j < rootClasses.size(); j++){
						int tmp = calTotalHeight(rootClasses.get(j));
						total2 += tmp;
						System.out.println(rootClasses.get(j)+":"+tmp);
					}
					ahh = (double)total2 / rootClasses.size();

					drawPyramid dp = new drawPyramid(nop, noc, nom, loc, cyclo, calls, fanout, andc, ahh);
					dp.drawpyramid(packageName);
				} else {
					System.err.println("Wrong parameter");
					return;
				}
			}

		} else if(pb == 2) {
			for(int i = 1; i < args.length; i++) {
				String p = args[i];
				File newfile = new File(p);
				if (newfile.isFile()) {
					String d = p;
					while (!new File(d).isDirectory() && d.length() > 0) {
						d = d.substring(0, d.length() - 1);
					}
					checkFile(newfile, d);
				} else {
					System.out.println("Wrong parameter");
					return;
				}
			}
		}
	}
	
	public static void openDir(File dir, String p) {
		String newpath = p + '/' + dir.getName();
		File newfile = new File(newpath);
		File newfileArray[] = newfile.listFiles();

		// ファイルの一覧
        for (File f: newfileArray){
            if(f.isFile()) {
            	System.out.println(f);
                checkFile(f, newpath);
            }
        }
        // フォルダの一覧
        for (File f: newfileArray){
            if(f.isDirectory()) {
            	System.out.println(f);
                openDir(f, newpath);
            }
        }
	}
	
	public static void checkFile(File f, String p) {
		String PathIncheckFile = p + '/' + f.getName();
		Path path = Paths.get(PathIncheckFile);
		List<String> lines;
		try {
			lines = Files.readAllLines(path ,
					StandardCharsets.ISO_8859_1);
		} catch (final Exception e) {
			System.err.println(e.getMessage());
			return;
		}

		for(int i = 0; i < lines.size(); i++){
			if(lines.get(i).contains("class")){
				List<String> strs = Arrays.asList(lines.get(i).split(" "));
				if(!strs.contains("class")) continue;
				String classname;
				classname = strs.get(strs.indexOf("class")+1);
				if(classname.charAt(classname.length()-1) == '{')
					classname = classname.substring(0, classname.length()-1);
				map.put(classname, new ArrayList<>());

				int index = strs.indexOf("extends");
				if(index > 0){
					index++;
					while(index < strs.size()){
						if(strs.get(index).equals("{") || strs.get(index).equals("implements")) break;
						String tmp = strs.get(index);
						if(tmp.charAt(tmp.length()-1) == ',' || tmp.charAt(tmp.length()-1) == '{')
							tmp = tmp.substring(0, tmp.length()-1);
						if(map.containsKey(tmp)) map.get(tmp).add(classname);
						index++;
					}
				}
				else rootClasses.add(classname);
			}
		}

		long lineCount;
		try {
			lineCount = Files.lines(path).count();
		} catch (final Exception e) {
			System.err.println(e.getMessage());
			return;
		}
		System.out.println("This File LOC : " + lineCount);

		//ソースコードから抽象木を作成
		final ASTParser parser = ASTParser.newParser(AST.JLS14);
		parser.setSource(String.join(System.lineSeparator(), lines).toCharArray());

		//木構造はunitにrootを表す。
		CompilationUnit unit;
		try {
			unit = (CompilationUnit) parser.createAST(new NullProgressMonitor());
		} catch (final Exception e) {
			System.err.println(e.getMessage());
			return;
		}

		final Visitor visitor = new Visitor(map);
		//深さ優先探索できる
		unit.accept(visitor);
		
		if(pb == 1) {
			nop += visitor.getnop();
			noc += visitor.getnoc();
			calls += visitor.getcalls();
			fanout += visitor.getfanout();
			nom += visitor.getnom();
			loc += lineCount;
			cyclo += visitor.getcyclo();
		}
		if(pb == 2)visitor.myprint(f.getName());
	}

	private static int calTotalHeight(String rootClass){
		if(map.get(rootClass).size() == 0) return 0;
		int max = Integer.MIN_VALUE;
		ArrayList<String> tmpList = map.get(rootClass);
		if(tmpList.size() == 1) max = 1 + calTotalHeight(tmpList.get(0));
		else{
			for(int i = 0; i < tmpList.size() - 1; i++){
				max = 1 + Math.max(calTotalHeight(tmpList.get(i)), calTotalHeight(tmpList.get(i+1)));
			}
		}
		return max;
	}
}