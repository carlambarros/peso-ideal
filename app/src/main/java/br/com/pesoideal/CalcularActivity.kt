package br.com.pesoideal

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.pesoideal.model.IMC

class CalcularActivity : AppCompatActivity() {

    private lateinit var image: ImageView
    private lateinit var txtIMC: TextView
    private lateinit var txtMsg: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calcular)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        image = findViewById(R.id.imgResult)
        txtIMC = findViewById(R.id.textImc)
        txtMsg = findViewById(R.id.textMsg)

        val btn = findViewById<Button>(R.id.btnResultado)
        val editPeso = findViewById<EditText>(R.id.editPeso)
        val editAltura = findViewById<EditText>(R.id.editAltura)
        val layout = findViewById<LinearLayout>(R.id.layoutResultado)
        btn.setOnClickListener {

            try {
                val peso = editPeso.text.toString().toDouble()
                val altura = editAltura.text.toString().toDouble()
                val valorIMC = calcularIMC(peso, altura)
                val imc = carregarIMC(valorIMC)
                txtIMC.text = "IMC: ${String.format("%.2f", valorIMC)}"
                image.setImageResource(imc.imagem)
                txtMsg.text = getString(imc.texto)
                layout.visibility = View.VISIBLE
            } catch (ex: NumberFormatException) {
                layout.visibility = View.GONE
                Toast.makeText(this, "Informe peso e altura", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun calcularIMC(peso: Double, altura: Double): Double {
        return peso / (altura * altura)
    }

    private fun carregarIMC(imc: Double): IMC {
        return when {
            imc < 18.5 -> IMC(R.drawable.img_peso_baixo, R.string.msg_abaixo_peso)
            imc < 25 -> IMC(R.drawable.img_peso_ideal, R.string.msg_normal)
            imc < 30 -> IMC(R.drawable.img_sobrepeso, R.string.msg_sobrepeso)
            imc < 35 -> IMC(R.drawable.img_obesidade_i, R.string.msg_obesidade_1)
            imc < 40 -> IMC(R.drawable.img_obesidade_ii, R.string.msg_obesidade_2)
            else -> IMC(R.drawable.img_obesidade_iii, R.string.msg_obesidade_3)
        }

    }
}