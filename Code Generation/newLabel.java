public abstract class newLabel{
    private String s;
    private static int noOfLoopLabels;
    private static int noOfIfLabels;
    private static int counter;

    public newLabel(){
        counter += 1;
        if (this.getClass().getName().endsWith("_loop")){
            if (counter >= 4){
                noOfLoopLabels += 1;
                counter = 0;
            }
            s = this.getClass().getName() + noOfLoopLabels;
        }
        else {
            if (counter >= 4){
                noOfIfLabels += 1;
                counter = 0;
            }
            s = this.getClass().getName() + noOfIfLabels;
        }

    }

    public static int getNoOfLoopLabels() {
        return noOfLoopLabels;
    }

    public String getS() {
        return s;
    }
}
class _else extends newLabel{}
class _then extends newLabel{}
class _exit extends newLabel{}
class start_loop extends newLabel{}
class enter_loop extends newLabel{}
class exit_loop extends newLabel{}
