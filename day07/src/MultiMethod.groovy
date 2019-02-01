class Employee{

    public void raise(Number number){
        System.out.println("Employee got raise");
    }
}

class Executive extends Employee{

    @Override
    public void raise(Number number){
        System.out.println("Executive got raise");
    }

    public void raise(BigDecimal bigDecimal){
        System.out.println("Executive got outlandish raise");
    }
}


def call(Employee employee){
        employee.raise(new BigDecimal(1000.00));
}



call(new Employee());
call(new Executive());
