package Ex;

public class ValueMain {

    public static void main(String[] args) {

        int a = 10;
        int b = a;   // 값을 복사

        a = 20;

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        Integer c = new Integer(10);
        Integer d = c;
        // Integer 값은 변경X

        System.out.println("c = " + c);
        System.out.println("d = " + d);

        int e = 10;
        int f = 10;

        System.out.println("e == f : " + (e == f));

        Address address1 = new Address("city", "street", "100000");
        Address address2 = new Address("city", "street", "100000");

        System.out.println("address1 == address2 : " + (address1 == address2));             // 동일성 비교
        System.out.println("address1.equals(address2) : " + (address1.equals(address2)));   // 동등성 비교
    }
}
