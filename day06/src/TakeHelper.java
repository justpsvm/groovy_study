
class Man implements Helper{
    @Override
    public boolean sendFlowers(){
        System.out.println("男人送花");
        return true;
    }
}

class Woman implements Helper{
    @Override
    public boolean sendFlowers(){
        System.out.println("女人送花");
        return true;
    }
}

class Dog implements Helper{

    @Override
    public boolean sendFlowers() {
        System.out.println("小狗送花");
        return true;
    }
}

abstract class Person {
    abstract boolean  sendFlowers();
}

interface Helper {
    boolean  sendFlowers();
}


public class Test{

//    static boolean takHelp(Person person){
//        return person.sendFlowers();
//    }

    static boolean takeHelp(Helper helper){
        return helper.sendFlowers();
    }

    public static void main(String[] args) {
//        takeHelp(new Man());
//        takeHelp(new Woman());

        takeHelp(new Man());
        takeHelp(new Woman());
        takeHelp(new Dog());

    }
}



