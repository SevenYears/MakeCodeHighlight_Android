package com.yqs.javacodeparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.yqs.javacodeparser.openfile.CallbackBundle;
import com.yqs.javacodeparser.openfile.OpenFileDialog;
import com.yqs.javacodeparser.rule.IRule;
import com.yqs.javacodeparser.style.SyntaxColorStyle;
import com.yqs.javacodeparser.syntaxcolor.RuleFactory;
import com.yqs.javacodeparser.syntaxcolor.StyleFactory;
import com.yqs.javacodeparser.syntaxcolor.SyntaxColorPainter;

import android.R.anim;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * MainActivity
 * 
 * @author YangQingshuai
 *
 */
public class MainActivity extends Activity {

	private EditText editText = null;
	
	private SyntaxColorPainter syntaxColorPainter = null;
	
	private static int OPEN_FILEDIALOG_ID = 0;
	
	private Button openButton = null;
	
	private Button resetButton = null;
	
	private File filePath = null;
	
	private String cur_path = null;
	
	private static boolean flag = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        filePath = this.getFilesDir();
        cur_path = filePath.toString();
        
        createDir();
        
        if(false == flag){
        	try {
				produceFiles();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	flag = true;
        }
         
        openButton = (Button)findViewById(R.id.Open);
        openButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//syntaxColorPainter.clearTokenList();		
				showDialog(OPEN_FILEDIALOG_ID);
			}
		});
        
        editText = (EditText)findViewById(R.id.Coder);
        DisplayMetrics dm = new DisplayMetrics();
        
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        editText.setWidth(dm.widthPixels);
        editText.setHeight(2*dm.heightPixels/3);
        editText.setBackgroundColor(Color.WHITE);
        editText.setHorizontallyScrolling(true);
        
        resetButton = (Button)findViewById(R.id.Reset);
        resetButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				editText.setText("");		
			}
		});
        
        addSyntaxColor();
    }
    
    public static final int JAVADOC_DEFAULT              = Color.rgb(98, 175, 244);
    
    public static final int SINGLE_LINE_COMMENT          = Color.rgb(116, 192, 123);
    
    public static final int KEYWORD                      = Color.rgb(128, 0, 128);
    
    public static final int STRING                       = Color.rgb(0, 0, 255);
    
    private IRule[] buildJavaRules(){
    	SyntaxColorStyle defauStyle = StyleFactory.fromDefault("Tahoma", 14);
    	
    	SyntaxColorStyle javaDocStyle = defauStyle.clone();
    	javaDocStyle.setForeground(JAVADOC_DEFAULT);
    	
    	SyntaxColorStyle commentStyle = defauStyle.clone();
    	commentStyle.setForeground(SINGLE_LINE_COMMENT);
    	
    	SyntaxColorStyle keywordStyle = defauStyle.clone();
    	keywordStyle.setForeground(KEYWORD);
    	keywordStyle.setBold(true);
    	
    	SyntaxColorStyle stringStyle = defauStyle.clone();
    	stringStyle.setForeground(STRING);
    	
    	return new IRule[] {
    			
    			RuleFactory.createMultiLineIRule("javadoc", "/**", "*/", javaDocStyle, "/**/"),
    			RuleFactory.createMultiLineIRule("comment", "/*", "*/", commentStyle),
    			
    			RuleFactory.createSingleLineRule("string", "\"", "\"", '\\', stringStyle),
    			RuleFactory.createSingleLineRule("string",  "\'", "\'", '\\', stringStyle),
    			RuleFactory.createSingleLineRule("comment", "//", commentStyle),
    			
    			RuleFactory.createKeywordRule("keyword", true, keywordStyle, new String[] {
    					"abstract", "boolean", "break", "byte", "case",
    	                "catch", "char", "class", "continue", "default", "do",
    	                "double", "else", "extends", "false", "final", "finally",
    	                "float", "for", "if", "implements", "import", "instanceof",
    	                "int", "interface", "long", "native", "new", "null", "package",
    	                "private", "protected", "public", "return", "short", "static",
    	                "super", "switch", "synchronized", "this", "throw", "throws",
    	                "transient", "true", "try", "void", "volatile", "while"})};
    }//buildJavaRules()

    private void addSyntaxColor(){
    	syntaxColorPainter = new SyntaxColorPainter(editText);
    	syntaxColorPainter.addRule(buildJavaRules());
    }
    
    @Override
    protected Dialog onCreateDialog(int id){
    	
    	if(id == OPEN_FILEDIALOG_ID){
			Map<String, Integer> images = new HashMap<String, Integer>();
			// 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
			images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);	// 根目录图标
			images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);	//返回上一层的图标
			images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);	//文件夹图标
			images.put("java", R.drawable.filedialog_file);	//文件图标
			images.put("txt", R.drawable.filedialog_file);
			images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);
	        
			Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {
				@Override
				public void callback(Bundle bundle) {
					String filepath = bundle.getString("path");

					//editText.setText(filepath);
				    InputStream inputStream = null;

					try {
						inputStream = new FileInputStream(filepath);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					StringBuffer sb = new StringBuffer("");
					String line = "";
					try {
						while((line = bufferedReader.readLine()) != null){
							sb.append(line).append("\n");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					editText.setText(sb.toString()+"\n");
				}
			}, 
			".java;.txt;", images, cur_path);
					
			return dialog;
		}
		return null;
    }
    
    private void produceFiles() throws IOException{
    	
    	String[] filesName = new String[]{"one.java", "two.java", "three.java", "four.txt"};
    	int[] ID = new int[]{R.raw.test, R.raw.test2, R.raw.test3, R.raw.test4};
    	
    	for(int i = 0; i < 4; ++i){
    		
    		File file = new File(filePath, filesName[i]);
    		file.createNewFile();
        	
        	InputStream inputStream = getApplicationContext().getResources().openRawResource(ID[i]);
        	
        	FileOutputStream outputStream = new FileOutputStream(file);

        	byte[] bytes = new byte[100];
        	
        	int len = 0;
        	while((len = inputStream.read(bytes))!=-1){
        		outputStream.write(bytes, 0, len);
        	}
        	
        	inputStream.close();
        	outputStream.close();
    	}		
    }
    
    private void createDir(){
        
    	if(android.os.Environment.getDataDirectory()==null){
    		return;
    	}
        String dir_path = android.os.Environment.
        		getExternalStorageDirectory().getAbsolutePath() + "/JavaFiles";
        filePath = new File(dir_path);
        if(!filePath.exists()){
        	if(filePath.mkdirs()){
        		cur_path = filePath.toString();
        	}
        	else {
				filePath = this.getFilesDir();
				cur_path = filePath.toString();
			}
        }
        else {
			cur_path = filePath.toString();
		}
        
    }
}
