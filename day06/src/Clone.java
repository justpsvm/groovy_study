class Engine implements Cloneable{
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class Car implements Cloneable{
    private Integer year;
    private Engine engine;
    @Override
    protected Object clone() {
        try {
            Car cloned = (Car)super.clone();
            Engine engineCloned = (Engine) engine.clone();
            cloned.engine = engineCloned;
            return cloned;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;    //没有必要的做法 只是为了满足编译器
        }
    }
}