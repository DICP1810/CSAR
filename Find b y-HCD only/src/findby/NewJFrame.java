/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package findby;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;

/**
 *
 * @author syc
 */
public class NewJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
    }
    //transfer mod for pd
    public String transferStringPD(String peptide,String pepMod){
        int pepLength=peptide.length();
        HashMap<String,Character> mod=new LinkedHashMap<>();
        mod.put("Oxidation",'1');
        mod.put("Carbamidomethyl",'2');

        mod.put("Methylamine",'3');
        mod.put("Agmat",'4');
        mod.put("Methy",'5');
        //
        //改变，因为修饰形式已改变
        if(pepMod.length()>5){
        if(pepMod.indexOf("\"")==0) 
            pepMod = pepMod.substring(1,pepMod.length());   //去掉第一个 "
        if(pepMod.lastIndexOf("\"")==(pepMod.length()-1)) 
            pepMod = pepMod.substring(0,pepMod.length()-1);  //去掉最后一个 " 
        }
        //System.out.println(pepMod);
        
        char[] mods=new char[pepLength+4];
        mods[0]='0';
        mods[1]='.';
        for(int i=0;i<pepLength;i++)
            mods[2+i]='0';
        mods[2+(int)pepLength]='.';
        mods[3+(int)pepLength]='0';
        int pos=0;
        //System.out.println("tedy");
        if(pepMod.contains(")")){
            //System.out.println("ttt");
            String[] modString=null;
            if(pepMod.indexOf(";")!=-1){
                modString=pepMod.split("; ");
                //System.out.println("tee");
            }
            else
            {
                System.out.println("KK");
                modString=new String[1];
                modString[0]=pepMod;
            }
            String[] singleMod;
            for(int j=0;j<modString.length;j++){
                //first extract the mod in the bracket
                //second extract the position of mod
                singleMod=modString[j].split("\\(");
                //去掉最后一个)
                //contains 不用加\\;而split需要加\\
                if(singleMod[1].contains(")")){
                   System.out.println("hh");
                   singleMod[1]=singleMod[1].substring(0,singleMod[1].length()-1);
                }
                
                System.out.println(singleMod[1]);
                if(singleMod[0].equals("N-Term")){
                    if(mod.containsKey(singleMod[1]))
                        mods[0]=mod.get(singleMod[1]);
 
                }
                else if(singleMod[0].equals("C-Term")){
                    if(mod.containsKey(singleMod[1]))
                        mods[pepLength+3]=mod.get(singleMod[1]); 
                }
                else{
                    //位置放这里,因为末端修饰没有位置
                    pos=Integer.parseInt(singleMod[0].substring(1));
                    if(mod.containsKey(singleMod[1]))
                        mods[pos+1]=mod.get(singleMod[1]);
                }
            }
        }
        return new String(mods);
    }
//transfer the string to number format of the modification
    public String transferStringPBuild(String peptide,String pepMod){
        int pepLength=peptide.length();
        HashMap<String,Character> mod=new LinkedHashMap<>();
        /*mod.put("Oxidation[M]#0",'1');
        mod.put("Propargylamine[AnyC-term]#0",'2');
        mod.put("PPA_DMAz#0",'3');
        mod.put("N2H4#0",'4');
        mod.put("N2H4-Guanidinyl#0",'5');
        mod.put("Guanidinyl[AnyN-term]#0",'6');
        mod.put("Guanidinyl[K]#0",'7');*/
        mod.put("Oxidation[M]#0",'1');
        mod.put("Methylamine[D]#0",'2');
        mod.put("Methylamine[E]#0",'3');
        mod.put("Methylamine[C-term]#0",'4');
        mod.put("Agmatine[C-term]#0",'5');
        mod.put("Agmatine[E]#0",'6');
        mod.put("Agmatine[D]#0",'7');
         
        //
        //
        if(pepMod.indexOf(';')!=-1){
        if(pepMod.indexOf("\"")==0) 
            pepMod = pepMod.substring(1,pepMod.length());   //去掉第一个 "
        if(pepMod.lastIndexOf("\"")==(pepMod.length()-1)) 
            pepMod = pepMod.substring(0,pepMod.length()-1);  //去掉最后一个 " 
        }
        //System.out.println(pepMod);
        
        char[] mods=new char[pepLength+4];
        mods[0]='0';
        mods[1]='.';
        for(int i=0;i<pepLength;i++)
            mods[2+i]='0';
        mods[2+(int)pepLength]='.';
        mods[3+(int)pepLength]='0';
        
        if(pepMod.indexOf(';')!=-1){
            String[] modString=pepMod.split(";");
            String[] singleMod;
            for(int j=0;j<modString.length;j++){
                singleMod=modString[j].split(",");
                if(singleMod[0].equals("0")){
                    if(mod.containsKey(singleMod[1]))
                        mods[0]=mod.get(singleMod[1]);
 
                }
                else if(Integer.parseInt(singleMod[0])==pepLength+1){
                    if(mod.containsKey(singleMod[1]))
                        mods[pepLength+3]=mod.get(singleMod[1]); 
                }
                else{
                    if(mod.containsKey(singleMod[1]))
                        mods[Integer.parseInt(singleMod[0])+1]=mod.get(singleMod[1]);
                }
            }
        }
        return new String(mods);
    }
  
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(jButton1)
                .addContainerGap(169, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jButton1)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
          // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("please select scan summary file");
        int result=chooser.showOpenDialog(this);
        String fileName,fileName1;
        if(result==JFileChooser.APPROVE_OPTION)
            fileName1 = chooser.getSelectedFile().getAbsolutePath();
        else
            return;
        try{
            BufferedReader pBuildFile =new BufferedReader(new FileReader(new File(fileName1)));
            FileWriter outFileL = new FileWriter(new File(fileName1+".pepL"));
            PrintWriter outL = new PrintWriter(outFileL);

            String dataRow;
            String outRow;
            String numberMod;
            pBuildFile.readLine();
            //pBuildFile.readLine();
            List<String> light=new ArrayList<>();
  
         
     
          
       
        
            ////////////////
            chooser.setDialogTitle("please select mgf file");
           result=chooser.showOpenDialog(this);
           if(result==JFileChooser.APPROVE_OPTION)
              fileName = chooser.getSelectedFile().getAbsolutePath();
           else
              return;
          
           
           /////////////////
            //outRow:spectrum1,spectrum2,pep,mod,mass
            outL.println("dta name"+"\t"+"peptide"+"\t"+"mod"+"\t"+"charge");
          int nm=0;
            while ((dataRow=pBuildFile.readLine())!=null)
            {
                nm=nm+1;
                String[] temp=dataRow.split("\\t");
                String dta=temp[26];
                String charge=temp[11];
                String peptide=temp[4].toUpperCase();
                String mod=temp[5];
                System.out.println(mod);
               
                //transfer mod to number format, temp1[2]:peptide sequence, temp1[4]: modification string format
                numberMod=transferStringPD(peptide,mod);
                outRow=dta+"\t"+peptide+"\t"+numberMod+"\t"+charge;
               //System.out.println(numberMod);
   
                    //pep seq+number mod+ms1 scan number
                light.add(outRow);

            }
     
         
            
            
            pBuildFile.close();
          
            
            ////////////////////////below is for label free!!!!!! calculate the ions
            //input for fixedModification
            List<Character> fixedModificationAminoAcid=new ArrayList<>();
            fixedModificationAminoAcid.add('C');
            List<Double> fixedModificationMassChange=new ArrayList<>();
            fixedModificationMassChange.add(57.021464);
            //fixedModificationMassChange.add(71.037114);
            //////////////
            //fixedModificationAminoAcid.add('K');
            //fixedModificationMassChange.add(28.0313);//dimethyl
            /////////////
            HashMap<Character,Double> fixedMod=new HashMap<>();
            /*for(int i=0;i<fixedModificationAminoAcid.size();i++)
            {
                fixedMod.put(fixedModificationAminoAcid.get(i),fixedModificationMassChange.get(i));
            }*/
            
            HashMap<Character,Double> varModL=new HashMap<>();
         

           //need to adjust the modification based on the data acquisition
           varModL.put('1',15.994915);//oxidation
           varModL.put('2',57.021464);//carbamy
           varModL.put('3',13.0316);//pppa-dmaz
           varModL.put('5',13.0316);//diamine
           varModL.put('4',112.111);//diamine gua
           
        
           //
           //
           double thres=0.02;
           //double thres=0.002;

       
                  
        
      
            System.out.println("show data");
     
           /////////////////////////////////////
          UnlabelCalc rel=new UnlabelCalc(new File(fileName),light,2);
           rel.setThreshold(thres);
           rel.setMod(varModL, fixedMod);
           rel.calculateAllLight();
           rel.writeData(new File(fileName+".HCD.prol.txt"));
           List<String> lightResult=rel.allResult;
           System.out.println(lightResult.size());
 
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        catch(IOException e){
            e.printStackTrace(System.out);       
        }
                                        
   
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
