import java.util.ArrayList;
import java.util.Collection;

public class UseingCollection {

    public static void main(String[] args) {

        ArrayList<String> arr1 = new ArrayList<>();
        Collection<String> arr2 = arr1;

        arr1.add("apple");
        arr1.add("huawei");
        arr1.add("xiaonmi");

//        arr1.remove(0);
//        arr2.remove(0);

        ((ArrayList<String>) arr2).remove(0);

        System.out.println(arr1.size());
        System.out.println(arr2.size());
    }
}
