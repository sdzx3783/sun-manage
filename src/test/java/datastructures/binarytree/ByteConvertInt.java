package datastructures.binarytree;


/**  
 * @Title:  Test3.java   
 * @Description:    byte转int 和 int转byte
 * @author: sunzhao  
 * @date:   2018年9月5日 下午4:14:57   
 */
public class ByteConvertInt {
	//真值为负时，其原码就是把最高位改为1，其余不变。反码就是最高位改为1，其余取反。补码就是在反码的基础上加1. 加1时记得是逢2进1.
	
	//负数的补码：可根据其整数的补码（正数的补码即源码即反码）所有位按位取反,然后+1)得到
	//根据负数补码得到负数的源码：对补码求补码（除符号位外，取反后加1） 计算值
	
	//java是使用补码来表示数值的
	//示例:
	//-2：2的补码 0000-0010 取反1111-1101 +1  1111-1110 得到 11111110
	//1111-1110: 补码1111-1110  最高位是1 说明是负数 ，除符号位取反1000-0001 加1 1000-0010得出原码
	
	
	public static void main(String[] args) {
		String property = System.getProperty("java.io.tmpdir");
		System.out.println(property);
		
		int parseInt = Integer.parseInt("11111111",2);
		System.out.println(parseInt);//补码：0000-0000 0000-0000 0000-0000 1111-1111
		byte b=(byte) parseInt;//int转byte 会截掉前24位：1111-1111
		System.out.println(b);//-1
		System.out.println((int) b);//byte转int 按符号位补位 1111-1111 1111-1111 1111-1111 1111-1111 所以还是-1
		System.out.println(Integer.toBinaryString(b));//byte参数被强转成int
		System.out.println(Integer.toBinaryString(b&0xff));//高位补0  0000-0000 0000-0000 0000-0000 1111-1111
		System.out.println("=====================================");
		
	}
}
