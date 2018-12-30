package formations;

import MyAnnotaion.MyAnnotation;

@MyAnnotation(Author = "zmc", Date = "2018/11/2")
public enum FormationType{
    CRANEWING("鹤翼"), GEESEFLYSHAPE("燕行"), XSHAP("冲轭"), LONGSNAKE("长蛇"), FISHSQUAMA("鱼鳞"),
    SQUARECIRCLE("方円"), CRESCENTMOON("偃月"), BOWANDARROW("锋矢");
    private final String name;
    private FormationType(String name){
        this.name = name;
    }
}