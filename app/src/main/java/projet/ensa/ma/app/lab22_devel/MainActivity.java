package projet.ensa.ma.app.lab22_devel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 🔹 Méthodes JNI existantes
    public native String helloFromJNI();
    public native int factorial(int n);
    public native String reverseString(String s);
    public native int sumArray(int[] values);

    // 🔹 Nouvelle méthode JNI (benchmark)
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

        // 🔹 JNI basique
        tvHello.setText(helloFromJNI());

        // 🔹 Factorial tests
        tvFact.setText(
                "factorial(10) = " + factorial(10) + "\n" +
                        "factorial(-5) = " + factorial(-5) + "\n" +
                        "factorial(20) = " + factorial(20)
        );

        // 🔹 Reverse
        tvReverse.setText("reverse(\"\") = [" + reverseString("") + "]");

        // 🔹 Sum array
        tvArray.setText("sum([]) = " + sumArray(new int[]{}));

        // BENCHMARK JAVA vs C++
        int n = 10000000;

        // Java
        long startJava = System.nanoTime();
        long sumJava = 0;
        for (int i = 0; i < n; i++) {
            sumJava += i;
        }
        long endJava = System.nanoTime();

        // C++
        long startNative = System.nanoTime();
        long sumNative = nativeSum(n);
        long endNative = System.nanoTime();

        // Affichage benchmark
        tvArray.append(
                "\n\n--- Benchmark ---\n" +
                        "Java sum = " + sumJava + "\n" +
                        "Time Java = " + (endJava - startJava) + " ns\n\n" +
                        "Native sum = " + sumNative + "\n" +
                        "Time C++ = " + (endNative - startNative) + " ns"
        );
    }
}

