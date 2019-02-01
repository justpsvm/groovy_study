ArrayList<String> arr1 = new ArrayList<>();
Collection<String> arr2 = arr1;

arr1.add("apple");
arr1.add("huawei");
arr1.add("xiaonmi");

//arr1.remove(0);
arr2.remove(0);

System.out.println(arr1.size());
System.out.println(arr2.size());