package code;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import java.util.*;

public class Visitor extends ASTVisitor {
	private ArrayList<String> methodName = new ArrayList<>();
	private ArrayList<String> tate = new ArrayList<>();
	private ArrayList<String> yoko = new ArrayList<>();
	private ArrayList<ArrayList<String>> list = new ArrayList<>();
	private int cnt = -1, l = 0;
	private int nop = 0, calls = 0, fanout = 0, nom = 0, cyclo = 0;
	private HashMap<String, ArrayList<String>> map;

	public Visitor(HashMap<String, ArrayList<String>> map){
		this.map = map;
	}

	@Override
	public boolean visit(TryStatement node) {
		cyclo++;
		return super.visit(node);
	}

	@Override
	public boolean visit(SwitchStatement node) {
		cyclo++;
		return super.visit(node);
	}

	@Override
	public boolean visit(WhileStatement node) {
		cyclo++;
		return super.visit(node);
	}

	@Override
	public boolean visit(ForStatement node) {
		cyclo++;
		return super.visit(node);
	}

	@Override
	public boolean visit(IfStatement node) {
		cyclo++;
		return super.visit(node);
	}
  
    @Override
	public boolean visit(PackageDeclaration node) {
		// TODO Auto-generated method stub
		nop++;
		return super.visit(node);
	}

	@Override
	public boolean visit(ClassInstanceCreation node) {
		// TODO Auto-generated method stub
		return super.visit(node);
	}

	@Override
	public boolean visit(SimpleName node) {
		// TODO Auto-generated method stub
		//System.out.println(node.toString());
		return super.visit(node);
	}
	

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		// TODO Auto-generated method stub
		//System.out.println(node.toString());
		
		return super.visit(node);
	}



	@Override
	public boolean visit(MethodInvocation node) {
		// TODO Auto-generated method stub
		l++;
	    System.out.print("");
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		int k = 0;
		int bracket = -1;
		cnt++;
		if(cnt != 0) {
			yoko.add(String.valueOf(l));
			calls += l;
			l = 0;
		}
		//System.out.println(node.toString());
		for(int i = 0; i < node.toString().length(); i++) {
			if(bracket < 0 && node.toString().charAt(i) == '(')bracket = i-1;
			if(node.toString().charAt(i) == '\n')k++;
		}
		int lastidx = -1, firstidx = -1;
		for(int i = bracket; i > 0; i--) {
			if(lastidx == -1){
				if(!Character.isLetterOrDigit(node.toString().charAt(i)))continue;
				else lastidx = i;
			}
			else if(firstidx == -1 && lastidx != -1 && Character.isSpaceChar(node.toString().charAt(i)))firstidx = i+1;
		}
		if(firstidx == -1)firstidx = 0;
		String name = "";
		for(int i = firstidx; i <= lastidx; i++) {
			name += node.toString().charAt(i);
		}
		methodName.add(name);
		tate.add(String.valueOf(k));
		nom += k;
		return super.visit(node);
	}

	public void myprint(String className) {
		yoko.add(String.valueOf(l));
		System.out.println("NOP : " + nop);
		System.out.println("NOM : " + (cnt+1));
		System.out.println("CYCLO : " + cyclo);
		for(int i = 0; i <= cnt; i++) {
			ArrayList<String> arr = new ArrayList<>();
			arr.add(methodName.get(i));
			arr.add(yoko.get(i));
			arr.add(tate.get(i));
			list.add(arr);
			System.out.println("--" +  methodName.get(i) + "--\ntate : " + tate.get(i));
			System.out.println("yoko : " + yoko.get(i));
		}
		System.out.println();

		drawBluePrint blue = new drawBluePrint(list);
		blue.draw(className);
	}
	
	public int getnop() {
		return nop;
	}
	public int getnoc() {
		return 1;
	}
	public int getcalls() {
		return calls;
	}
	public int getfanout() {
		return 0;
	}
	public int getnom() {
		return nom;
	}
	public int getcyclo() {
		return cyclo;
	}
	
	//右クリックsourse overrideで追加
	
}
