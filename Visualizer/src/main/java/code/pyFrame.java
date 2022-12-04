package code;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * layoutを設定してパネルを作成するクラス（Window表示）
 * @author inory
 * pyBoardViewを呼び出し
 */
public class pyFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public pyFrame(drawPyramid dPy, String packageName) {		//

		setTitle(packageName);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//ウィンドウ内部を占有する「ベース」パネルを作成する
		JPanel base = new JPanel();
		setContentPane(base);
		base.setPreferredSize(new Dimension(900,300));//希望サイズの指定
		setMinimumSize(new Dimension(600,200));//最小サイズの指定
		
		//setting panel layout
		setLayout(new BorderLayout());
		pyBoardView pyView = new pyBoardView(dPy);
		add(pyView,BorderLayout.CENTER);
		
		pack();//ウィンドウに乗せたものの配置を確定する
		setVisible(true);//ウィンドウを表示する
	}

}
