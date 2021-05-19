package com.arnxxau.kirukalk

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout = findViewById<LinearLayout>(R.id.LAYOUT)

        layout.setOnClickListener {
            hideKeyboard(it)
        }






        val noteExtra = arrayOf(0.1,0.2)
        val selector1 = findViewById<AutoCompleteTextView>(R.id.spinner1)
        val selector2 = findViewById<AutoCompleteTextView>(R.id.spinner2)
        val adapter = ArrayAdapter(this, R.layout.drop_down_menu, noteExtra)

        selector1.setAdapter(adapter)
        selector2.setAdapter(adapter)










        val ca = findViewById<TextInputLayout>(R.id.input1)
        val es = findViewById<TextInputLayout>(R.id.input2)
        val ll = findViewById<TextInputLayout>(R.id.input3)
        val h = findViewById<TextInputLayout>(R.id.input4)
        val o = findViewById<TextInputLayout>(R.id.input5)
        val e1 = findViewById<TextInputLayout>(R.id.input6)
        val e2 = findViewById<TextInputLayout>(R.id.input7)
        val batx = findViewById<TextInputLayout>(R.id.nota)
        val Btn = findViewById<com.google.android.material.button.MaterialButton>(R.id.calcBtn)
        val displayer = findViewById<TextView>(R.id.viewer)


        val general = arrayOf(ca,es,ll,h,o)
        val específiques = arrayOf(e1,e2)

        Btn.setOnClickListener {



            displayer.text = noteCalc(general,específiques,batx)

        }







    }

    fun hideKeyboard (v: View){
        val IMM: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        IMM.hideSoftInputFromWindow(v.windowToken,0)

    }



    fun noteCalc(g: Array<TextInputLayout>, e: Array<TextInputLayout>, b: TextInputLayout): String {

        var string = ""

        var espINT = arrayListOf<Float>()
        var geINT = arrayListOf<Float>()
        var generalB = true
        var batxB = true
        var finalTRU = false
        var batxNOTEfloat: Float = 0F
        var NOTAFINAL = 0F
        val displayer = findViewById<TextView>(R.id.viewer)




        for (contents in g){
            if (contents.editText?.text != null && contents.editText?.text.toString().isNotEmpty()){
                geINT.add(contents.editText!!.text.toString().toFloat())
            }else{
                generalB = false
            }
        }


        for (contents in e){
            if (contents.editText?.text != null && contents.editText?.text.toString().isNotEmpty()){
                espINT.add(contents.editText!!.text.toString().toFloat())
            }
        }


        if (b.editText?.text != null && b.editText?.text.toString().isNotEmpty()){
            batxNOTEfloat = b.editText!!.text.toString().toFloat()
        } else{
            batxB = false
        }


        val e1 = findViewById<AutoCompleteTextView>(R.id.spinner1)
        var spinner1VALUE: Float = if (e1.text.toString().isNotEmpty()){
            e1.text.toString().toFloat()
        }else{
            0F
        }


        val e2 = findViewById<AutoCompleteTextView>(R.id.spinner2)
        var spinner2VALUE: Float = if (e2.text.toString().isNotEmpty()){
            e2.text.toString().toFloat()
        }else{
            0F
        }





        if (batxB && generalB){

            var faseGeneral = 0F
            for (i in geINT){
                faseGeneral+=i
            }

            var esp1 = 0F
            var esp2 = 0F

            for (i in 0 until espINT.size){
                if (i == 0){
                    esp1 = espINT[i]
                }

                if (i == 1){
                    esp2 = espINT[i]
                }
            }

            val mitjanaGENERAL = faseGeneral/geINT.size

            if (mitjanaGENERAL < 5){
                finalTRU = true
            }






            NOTAFINAL = (batxNOTEfloat*0.6 + (mitjanaGENERAL)*0.4
                    + esp1*spinner1VALUE + esp2*spinner2VALUE).toFloat()
        }







        string = when{

            !batxB -> "Falta la nota de batx"
            batxNOTEfloat < 5 -> "Has d'aprovar batxillerat amb un 5"
            !generalB -> "Falta alguna nota"
            finalTRU -> "La general ha de donar més de 5"
            else -> {
                NOTAFINAL.toString()
            }

        }


        if (!batxB || !generalB || batxNOTEfloat < 5 || finalTRU){
            displayer.textSize = 25F
        }else{
            displayer.textSize = 40F
        }







        return string
    }



}