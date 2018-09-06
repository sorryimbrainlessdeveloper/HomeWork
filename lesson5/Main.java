package java_core.lesson5;

public class Main  {
    private static final int size = 10000000;
    private static final int h = size / 2;

    public static void main(String[] args) {
        Main main = new Main();
        main.firstThread();
        main.secondThread();
    }

    public float[] someMethod(float arr[]){
        //long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + arr[i] / 5) * Math.cos(0.2f + arr[i] / 5) * Math.cos(0.4f + arr[i] / 2));
        }
        //System.out.println("время на заполнение первого массива - " + (System.currentTimeMillis() - a));
        return arr;
    }

    public void firstThread(){
        float[] arr = new float[size];
        long a = System.currentTimeMillis();
        for (int i = 0; i < size; i++)arr[i] = 1.0f;
            someMethod(arr);
        System.out.println("расчет времени на заполнение массива одним методом - " + (System.currentTimeMillis() - a));
    }

    public void secondThread(){
        float[] arr = new float[size];
        long a = System.currentTimeMillis();
        float arr1[] = new float[h];
        float arr2[] = new float[h];
        for (int i = 0; i < arr.length; i++) arr[i] = 1.0f;
        System.arraycopy(arr,0,arr1,0,h);
        System.arraycopy(arr,h,arr2,0,h);

        new Thread(){
            @Override
            public void run() {
                float[] a1 = someMethod(arr1);
                System.arraycopy(a1,0,arr1,0,a1.length);
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                float[] a2 = someMethod(arr2);
                System.arraycopy(a2,0,arr2,0,a2.length);
            }
        }.start();
        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, 0,h);
        System.out.println("расчет времени на заполнение массива двумя потоками - " + (System.currentTimeMillis() - a));
    }
}
