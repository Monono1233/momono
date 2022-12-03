import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.lang.*;

public class UserTest {
    public static void main(String[] args){
        //创建键盘录入对象
        Scanner sc = new Scanner(System.in);
        //新建集合存放所有方法内容
        ArrayList<User> list = new ArrayList<>();
        //首页循环录入

        //新建一个用户
        //User u1 = new User("monono1","310110199805225154","18918358952","asdasd");
        //放进集合
        //list.add(u1);

        loop: while(true){
            System.out.println("----------------------欢迎访问本网站-------------------");
            System.out.println("1.注册");
            System.out.println("2.登录");
            System.out.println("3.忘记密码");
            System.out.println("4.退出");
            
            System.out.println("请您做出选择");
            String choose = sc.next();
            switch(choose){
                case "1":{
                    userRegister(list);
                    //查看是否存入本list
                    System.out.println("数据库内容如下");
                    System.out.println();
                    for (int i = 0; i < list.size(); i++) {
                        User u = list.get(i);
                        System.out.println(u.getUserName()+","+u.getId()+","+u.getPhone()+","+u.getPassword());
                    }
                    continue;
                }
                case "2":{
                    userLogin(list);
                    break loop;
                }
                case "3":{
                    userForgetPassword(list);
                    //修改成功之后 返回修改后的密码
                    System.out.println("密码修改后内容如下");
                    System.out.println();
                    for (int i = 0; i < list.size(); i++) {
                        User u = list.get(i);
                        System.out.println(u.getUserName()+","+u.getId()+","+u.getPhone()+","+u.getPassword());
                    }
                    continue;
                }
                case "4":{
                    System.out.println("退出系统");
                    break loop;
                }
                default:{
                    System.out.println("没有这个选项，请重新选择");
                }
            }
        }
        

    }

    //新建所有方法填充sout
    public static void userRegister(ArrayList<User> list){
        Scanner sc = new Scanner(System.in);
        User u = new User();
        System.out.println("请录入用户名");
        String userName = sc.next();
        //判断用户名是否已经存在 若存在则提示无法注册 不存在可以继续进行
        boolean flag = contains(list, userName);
        if(flag){
            System.out.println("用户名已经存在，无法注册。");
        }else{
            //如果用户名不存在，则进行后续验证
            //用户名是否合法isLegal()
            boolean userNameFlag = userNameIsLegal(userName);
            
            //用户名合法时 用户输入成立
            if(userNameFlag){
                //放进username
                u.setUserName(userName);
                System.out.println("用户名合法，请输入身份证");
                //录入身份证
                String id = sc.next();
                //判断身份证是否合法 userIdIsLegal()
                boolean userIdFlag = userIdIsLegal(id);

                if(userIdFlag){
                    //身份证合法 填入对象
                    u.setId(id);
                    System.out.println();
                    System.out.println("身份证已录入，请继续录入手机号");
                    
                    //循环输入手机号 若手机号无误则跳出循环
                    while(true){
                        String phone = sc.next();
                        boolean userPhoneFlag = userPhoneIsLegal(phone);
                        if(userPhoneFlag){
                            u.setPhone(phone);
                            break;
                        }else{
                            System.out.println("手机号不符合规范！请重新输入");
                        }
                    }
                    //输入密码 以及验证
                    int passwordCount = 3;
                    String userPassword = null;

                    while(passwordCount>0){
                        System.out.println("请输入密码！");
                        userPassword = sc.next();
                        //循环输入密码 若成功则退出循环
                        if(userPassword.length()!=0){
                            if(passwordCount>0){
                                System.out.println("请再次确认密码！");
                                String resUserPassword = sc.next();
                                //判断密码是否合法的方法 userPasswordIsLegal()
                                boolean userPasswordFlag = userPasswordIsLegal(userPassword,resUserPassword);
                                //共三次机会
                                if(userPasswordFlag){
                                    //前后两次密码一致 随机生成验证码 判断验证码是否也一致
                                    String code = getCode();
                                    System.out.println("验证码为"+code+"，请输入验证码！");
                                    //输入验证码
                                    String resCode = sc.next();
                                    if(resCode.equals(code)){
                                        //验证码一致 注册成功
                                        System.out.println("注册成功！");
                                        System.out.println();
                                        //录入密码
                                        //u.setPassword(userPassword);
                                        break;
                                    }else{
                                        passwordCount--;
                                        System.out.println("密码/验证码输入错误，请重新输入，还剩下"+passwordCount+"次机会");
                                        System.out.println();
                                    }
                                }else{
                                    System.out.println("请检查密码是否正确");
                                    System.out.println();
                                    continue;
                                }
                            }else{//已经没有机会 退出系统
                                System.out.println("机会用完，注册失败");
                                System.exit(0);
                            }
                        }else{
                        System.out.println("密码不得为空！");
                        }
                    }
                    //均验证完毕 录入密码
                    u.setPassword(userPassword);
                    //将对象添加到集合
                    list.add(u);
                    System.out.println("-----------------以下内容已存入后台数据库----------------");
                    System.out.println();
                    System.out.println("用户名\t身份证\t\t电话\t密码");
                    System.out.println(u.getUserName()+","+u.getId()+","+u.getPhone()+","+u.getPassword());
                    System.out.println("-------------------------------------------------------");
                    System.out.println();

                }else{
                    System.out.println("身份证录入有误，返回用户名录入界面！");
                    System.out.println();
                }
            }else{
                System.out.println("用户名不合法，请重输");
            }
        }
    }

    public static void userLogin(ArrayList<User> list){
        //登录
        //录入用户名
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入已注册的用户名");
        System.out.println();
        String userName = sc.next();
        boolean flag = userNameIsLegal(userName);
        //判断是否合法 如果合法则继续下一步 不合法则重输
        while(true){
            if(!flag){
                System.out.println("用户名不合法，请重新输入！");
                System.out.println();
            }else{
                //用户名合法 继续验证是否已经注册
                int index = getIndex(list, userName);
                //若index不为-1 则代表已经注册 跳出循环
                if(index>=0){
                    break;
                }else{
                    //用户名合法但不在list中，重新输入
                    System.out.println("用户未注册。");
                    System.exit(0);
                }
            }
        }
        //验证成功 继续输密码和验证码 判断和list中userName对应的密码是否一致 且验证码是否输入正确
        //已有userName和所在index
        System.out.println("请输入密码");
        String password = sc.next();  
        //确认密码是否合法 再匹配 3次机会
        while(true){
            if(password.length()==0){
                System.out.println("密码长度不符，请重输！");
            }else{
                //长度符合 判断是否在list内
                System.out.println("长度符合规范！");
                System.out.println();
                for (int i = 0; i < 3; i++) {
                    User u = list.get(getIndex(list, userName));
                    String code = getCode();
                    
                    //在list内 则提示密码正确 确认验证码
                    if(password.equals(u.getPassword())){
                        System.out.println("密码输入无误，请输入验证码 "+code);
                        String inputCode =sc.next();
                        if(inputCode.equals(code)){
                            System.out.println("验证码正确，登录成功！");
                            break;
                        }else{
                            System.out.println("验证码有误，还剩"+(3-i)+"次机会");
                            System.out.println();
                        }
                    }else{
                        System.out.println("密码输入错误，还剩"+(3-i)+"次机会");
                        System.out.println();
                    }
                }
                break;
            }
        }
    }
    public static void userForgetPassword(ArrayList<User> list){
        //录入用户名 判断用户名是否存在
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入已注册的用户名");
        System.out.println();
        String userName = sc.next();
        boolean flag = userNameIsLegal(userName);
        //判断是否合法 如果合法则继续下一步 不合法则重输
        while(true){
            if(!flag){
                System.out.println("用户名不合法，请重新输入！");
                System.out.println();
            }else{
                //用户名合法 继续验证是否已经注册
                int index = getIndex(list, userName);
                //若index不为-1 则代表已经注册 跳出循环
                if(index>=0){
                    break;
                }else{
                    //用户名合法但不在list中，重新输入
                    System.out.println("用户未注册。");
                    System.exit(0);
                }
            }
        }
        //用户名验证成功 循环录入身份证
        while(true){
            System.out.println("请输入用户身份证");
            String id = sc.next();

            //判断身份证是否合法
            boolean idFlag = userIdIsLegal(id);
            if(!idFlag){
                System.out.println("身份证不合法，请重输！");
                System.out.println();
            }else{
                //身份证合法 确认是否匹配用户身份证
                User u = list.get(getIndex(list, userName));
                if(id.equals(u.getId())){
                    System.out.println("请输入用户手机！");
                    System.out.println();
                    String phone = sc.next();
                    if(phone.equals(u.getPhone())){
                        System.out.println("手机号匹配，请修改密码！");
                        String newPassword = sc.next();
                        u.setPassword(newPassword);
                        System.out.println("修改成功！");
                        break;
                    }else{
                        //修改失败
                        System.out.println("手机号有误，账号信息不匹配，修改失败！");
                        System.exit(0);
                    }
                }else{
                    System.out.println("身份证不匹配，请重输！");
                }
            }
        }
    }
    //获取对应索引方法getIndex
    public static int getIndex(ArrayList<User> list,String userName){
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            String name =u.getUserName();
            if(name.equals(userName)){
                return i; //依据用户名查找索引
            }
        }
        return -1;
    }
    //查询当前用户名是否存在
    public static boolean contains(ArrayList<User> list,String userName){
        return getIndex(list, userName)>=0;
    }
    //判断用户名合法性
    public static boolean userNameIsLegal(String userName){
        //长度
        int letterCount = 0;
        loop:while(true){
            if(userName.length()>=3 || userName.length()<=15){
                //符合长度规范
                String newUserName = userName.toLowerCase();
                for (int i = 0; i < newUserName.length(); i++) {
                    char c = newUserName.charAt(i);
                    if((c>'a'&&c<'z') ||(c>'0'&&c<'9')){
                        //符合规范 判断字母个数是否大于等于0
                        if(c>'a'&&c<'z'){
                            letterCount++;
                        }
                    }else{
                        System.out.println("用户名内容不符合规范！");
                        return false;
                    }
                }
                //满足所有要求后
                if(letterCount>=0){
                    //System.out.println("输入的用户名为"+userName+"。");
                    break loop;
                }
            }else{
                //不符合长度规范
                System.out.println("用户名长度不符合规范！");
                return false;
            }
        }
        return true;
        
    }

    public static boolean userPasswordIsLegal(String s1,String s2){
        if(s1.equals(s2)){
            return true;
        }else{
            return false;
        }       
    }

    public static boolean userIdIsLegal(String str){
        if(str.length()!=18){
            //System.out.println("身份证长度有误。");
            return false;
        }else{
            //首位不能是0
            if(str.charAt(0)=='0'){
                System.out.println("首位不能为0！");
                return false;
            }else{
                //前17位不能有字母
                for (int i = 0; i < str.length()-1; i++) {
                    char c = str.charAt(i);
                    if(c>='0'&&c<='9'){
                        char last = str.charAt(str.length()-1);
                        if((last>='0'&&last<='9') || (last =='x') ||(last=='X')){
                            //尾号无误
                            break;
                        }else{
                            System.out.println("身份证尾数有误！");
                            return false;
                        }
                        
                    }else{
                        System.out.println("身份证前17位出现字母！");
                        return false;
                    }
                }
                System.out.println("身份证认证通过！");
                return true;
            }
        }
    }

    public static boolean userPhoneIsLegal(String str){
        if(str.length()!=11){
            //System.out.println("长度不符合规范！");
            return false;
        }else{
            //长度符合规范 比较首位
            char first = str.charAt(0);
            if(first=='0'){
                System.out.println("首位不得为0！");
                return false;
            }else{
                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if(c>='0'&&c<='9'){
                       break;
                    }else{
                        System.out.println("手机号不符合规范！");
                        return false;
                    }
                }
                return true;
            }
        }
    }
    //获取验证码
    public static String getCode(){
        //五位 随机生成4字母 1数字位置随意
        
        //随机拼接4字母再拼1数字 最后打乱
        char[] chs = new char[52];
        for (int i = 0; i < chs.length/2; i++) {
            chs[i]=(char)('a'+i);
        }
        //注意i的大小 由于变量仍然是i 第二轮从A开始录入大写字母时 计数器i要置为（i-24）
        for (int i = chs.length/2; i < chs.length; i++) {
            chs[i]=(char)('A'+i-26);
        }

        StringBuilder sb = new StringBuilder();
        //前四位都是字母 随机取
        for (int i = 0; i < 4; i++) {
            Random r = new Random();
            int letter = r.nextInt(52);
            sb.append(chs[letter]);
        }
        Random r1 = new Random();
        int index = r1.nextInt(10);
        String res = sb.append(index).toString();
        String code = shuffle(res);

        return code;
    }
	public static String shuffle(String str){
        //字符串转为字符数组
        char[] arr = str.toCharArray();
        Random r = new Random();
        int index = r.nextInt(4);
        //最后一位和前面任何一个位置转换顺序
        char temp = arr[arr.length-1];
        arr[arr.length-1] = arr[index];
        arr[index] = temp;
        
        //builder拼接
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
        }
        String res = sb.toString();
        return res;
    }
}
