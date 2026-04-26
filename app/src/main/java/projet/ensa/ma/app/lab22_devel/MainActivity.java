package projet.ensa.ma.app.lab22_devel;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 🔹 JNI methods
    public native String helloFromJNI();
    public native int factorial(int n);
    public native String reverseString(String s);
    public native int sumArray(int[] values);
    public native long nativeSum(int n);

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvHello = findViewById(R.id.tvHello);
        TextView tvFact = findViewById(R.id.tvFact);
        TextView tvReverse = findViewById(R.id.tvReverse);
        TextView tvArray = findViewById(R.id.tvArray);

        // 🔹 Hello JNI
        tvHello.setText("🔹 Hello JNI\n\n" + helloFromJNI());

        // 🔹 Factorial
        tvFact.setText(
                "🔹 Factorial\n\n" +
                        "10! = " + factorial(10) + "\n" +
                        "-5 → " + factorial(-5) + " (erreur)\n" +
                        "20 → " + factorial(20) + " (overflow)"
        );

        // 🔹 Reverse
        tvReverse.setText(
                "🔹 Reverse String\n\n" +
                        "Input : \"\"\n" +
                        "Output : [" + reverseString("") + "]"
        );

        // 🔹 Sum Array
        tvArray.setText(
                "🔹 Sum Array\n\n" +
                        "sum([]) = " + sumArray(new int[]{})
        );

        // 🔥 Benchmark
        int n = 10000000;

        long startJava = System.nanoTime();
        long sumJava = 0;
        for (int i = 0; i < n; i++) {
            sumJava += i;
        }
        long endJava = System.nanoTime();

        long startNative = System.nanoTime();
        long sumNative = nativeSum(n);
        long endNative = System.nanoTime();

        tvArray.append(
                "\n\n📊 Benchmark\n\n" +
                        "Java:\n" +
                        "Sum = " + sumJava + "\n" +
                        "Time = " + (endJava - startJava) + " ns\n\n" +
                        "C++:\n" +
                        "Sum = " + sumNative + "\n" +
                        "Time = " + (endNative - startNative) + " ns"
        );
    }
}

