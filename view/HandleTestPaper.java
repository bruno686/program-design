package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import data.*;
public class  HandleTestPaper implements ActionListener{
    TestPaperView  view;
    TestPaper   testPaper;   //需要处理的试卷
    Problem problem;         //当前的题目
    Toolkit tool;            //处理图像
    public HandleTestPaper(){
        tool = Toolkit.getDefaultToolkit();
    }
    public void setView(TestPaperView view) {
       this.view = view;
    }
    public void setTestPaper(TestPaper testPaper) {
       this.testPaper = testPaper;
    }
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==view.stratExam){
            view.time.start(); //开始计时
            view.mustKnow.setVisible(false);
            view.showContent.setVisible(false);
            view.stratExam.setVisible(false);
            view.nextProblem.setVisible(true);
            view.previousProblem.setVisible(true);
            //view.wa.setVisible(false);
            if(testPaper!=null){
                problem = testPaper.nextProblem();
                handleProblem(problem);
            }
            else {
                JOptionPane.showMessageDialog
                        (view,"没有试题","消息对话框",JOptionPane.WARNING_MESSAGE);
            }
        }

       if(e.getSource()==view.nextProblem){
           view.group.clearSelection();
           view.viewAnswer.setVisible(false);
          //view.time.start(); //开始计时
          if(testPaper!=null){
            problem = testPaper.nextProblem();
            String NextSelected = problem.getUserAnswer();
              if(!NextSelected.isEmpty()){
                  if(NextSelected.equalsIgnoreCase("A")){
                      view.choiceA.setSelected(true);
                  }
                  if(NextSelected.equalsIgnoreCase("B")){
                      view.choiceB.setSelected(true);
                  }
                  if(NextSelected.equalsIgnoreCase("C")){
                      view.choiceC.setSelected(true);
                  }
                  if(NextSelected.equalsIgnoreCase("D")){
                      view.choiceD.setSelected(true);
                  }
              }
            handleProblem(problem);
          }
          else {
             JOptionPane.showMessageDialog
              (view,"没有试题","消息对话框",JOptionPane.WARNING_MESSAGE);
          }
       } 
       if(e.getSource()==view.previousProblem){
          view.group.clearSelection();
         // view.time.start(); //开始计时
          if(testPaper!=null){
            problem = testPaper.previousProblem();
            String PreSelected = problem.getUserAnswer();
            if(!PreSelected.isEmpty()){
                if(PreSelected.equalsIgnoreCase("A")){
                    view.choiceA.setSelected(true);
                }
                if(PreSelected.equalsIgnoreCase("B")){
                    view.choiceB.setSelected(true);
                }
                if(PreSelected.equalsIgnoreCase("C")){
                    view.choiceC.setSelected(true);
                }
                if(PreSelected.equalsIgnoreCase("D")){
                    view.choiceD.setSelected(true);
                }
            }
            handleProblem(problem);
          }
          else{
            JOptionPane.showMessageDialog
              (view,"没有试题","消息对话框",JOptionPane.WARNING_MESSAGE);
          }
          
       }
       if(e.getSource()==view.viewAnswer){   //查阅答案
           JOptionPane.showMessageDialog
                    (view,"您的答案:"+problem.getUserAnswer()+"正确答案:"+problem.getCorrectAnswer(),"消息对话框",JOptionPane.WARNING_MESSAGE);
           view.viewAnswer.setVisible(false);
       }
       if(e.getSource()==view.aProblemSubmit){  //确认一道题目的答案
           String answer ="";

          if(view.choiceA.isSelected()){
             answer = answer+"A";
          }
          if(view.choiceB.isSelected()){
             answer = answer+"B";
          }
          if(view.choiceC.isSelected()){
             answer = answer+"C";
          }
          if(view.choiceD.isSelected()){
             answer = answer+"D";
          }
          if(problem==null) {
              JOptionPane.showMessageDialog
              (view,"没有试题","消息对话框",JOptionPane.WARNING_MESSAGE);
             return;
          }
          if(answer != ""){
              view.viewAnswer.setVisible(true);
              view.choiceA.setVisible(false);
              view.choiceB.setVisible(false);
              view.choiceC.setVisible(false);
              view.choiceD.setVisible(false);
              view.aProblemSubmit.setVisible(false);
           }
          else{
              JFrame f = new JFrame(); //创建一个窗体
              f.setVisible(false);
              f.setSize(200, 200);//设置好宽高
              f.setLocationRelativeTo(null);//窗体居中显示
              f.setBackground(Color.RED) ;    // 将背景设置成白色
              JLabel label1 = new JLabel("请选择答案");
              label1.setHorizontalAlignment(0);
              f.add(label1);
              f.setVisible(true);
          }
          problem.setUserAnswer(answer);
       } 
       if(e.getSource()==view.submit){
           testPaper.acceptTeacher(view.teacher); //试卷让老师批阅
           //view.renewJButton.setVisible(true);
           view.submit.setVisible(false);
           view.time.stop(); 
           view.showUsedTime.setText("交卷了");
       } 
       /*if(e.getSource()==view.renewJButton) {     //再来一套题目
           view.showUsedTime.setText("");
           view.usedTime = view.totalTime;
           view.showUsedTime.setText("考试剩余时间:"+view.totalTime);
           view.showContent.setText(null);
           Image img = tool.getImage("软件发布/图像管理/renew.jpg");
           handleImage(img); 
           view.showImage.repaint();
           view.nextProblem.setVisible(true); 
           view.previousProblem.setVisible(true); 
           String problemSource= testPaper.getProblemSource(); //得到原始题库
           GiveTestPaper initTestPaper = new RamdomInitTestPaper(); 
           testPaper=initTestPaper.getTestPaper(problemSource,testPaper.getProlemAmount());
           view.renewJButton.setVisible(false);
           view.submit.setVisible(true);  
       }*/
       view.choiceA.setSelected(false);
       view.choiceB.setSelected(false);
       view.choiceC.setSelected(false);
       view.choiceD.setSelected(false);
    }
    private void handleProblem(Problem problem) {
          if(problem==null) {
              JOptionPane.showMessageDialog
              (view,"没有试题","消息对话框",JOptionPane.WARNING_MESSAGE);
          }
          else {
              view.aProblemSubmit.setVisible(true);
              view.showContent.setText(problem.getContent());
              view.showContent.setVisible(true);
              view.showContent.setEditable(false);
              if(problem.getIsChoice()) {
                   handelChoice();
              }
              else if(problem.getIsJudge()) {
                   handelJudge();
              }
              //如果非多选，加入单选按钮组
              if(!problem.getContent().contains("多选")){
                  //System.out.println(problem.getContent());
                  view.group.add(view.choiceA);
                  view.group.add(view.choiceB);
                  view.group.add(view.choiceC);
                  view.group.add(view.choiceD);
              }else{ //如果为多选，移除多选按钮组
                  view.group.remove(view.choiceA);
                  view.group.remove(view.choiceB);
                  view.group.remove(view.choiceC);
                  view.group.remove(view.choiceD);
              }
              String imageName = problem.getImageName();
               //用户将必须把图像存放到"图像管理"文件夹
              Image img = tool.getImage("软件发布/图像管理/"+imageName);
              handleImage(img);
              //view.group.clearSelection();//清除下一题仍旧沿袭选择的情况
          }
    }
    private void handelJudge() {
          view.choiceA.setText(problem.getGiveChoiceA());
          view.choiceB.setText(problem.getGiveChoiceB());
          view.choiceA.setVisible(true);
          view.choiceB.setVisible(true);
          view.choiceC.setVisible(false);
          view.choiceD.setVisible(false);
    }
    private void handelChoice() {
          view.choiceA.setText(problem.getGiveChoiceA());
          view.choiceB.setText(problem.getGiveChoiceB());
          view.choiceC.setText(problem.getGiveChoiceC());
          view.choiceD.setText(problem.getGiveChoiceD());
          view.choiceA.setVisible(true);
          view.choiceB.setVisible(true);
          view.choiceC.setVisible(true);
          view.choiceD.setVisible(true);
    }
    private void handleImage(Image image) {
          view.showImage.setImage(image);
          view.showImage.repaint();
    }
}