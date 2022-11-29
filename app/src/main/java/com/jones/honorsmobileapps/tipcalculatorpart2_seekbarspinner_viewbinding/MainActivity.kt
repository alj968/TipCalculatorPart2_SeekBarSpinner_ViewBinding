package com.jones.honorsmobileapps.tipcalculatorpart2_seekbarspinner_viewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.jones.honorsmobileapps.tipcalculatorpart2_seekbarspinner_viewbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var subtotal = 0
    var numGuests = 0
    var tipPercent = 0
    var finalTotal = 0.0

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        subtotal = 100;

        setUpRadioButtons()
        setUpTipSeekBar()
        setUpNumOfGuestsSpinner()
    }

    fun setUpRadioButtons() {
        val listOfRadioButtons =
            listOf(binding.radioButtonTen, binding.radioButtonFifteen, binding.radioButtonEighteen, binding.radioButtonTwentyFive)
        for (radioButton in listOfRadioButtons) {
            radioButton.setOnClickListener {
                when (radioButton.id) {
                    R.id.radioButtonTen -> tipPercent = 10
                    R.id.radioButtonFifteen -> tipPercent = 15
                    R.id.radioButtonEighteen -> tipPercent = 18
                    R.id.radioButtonTwentyFive -> tipPercent = 25
                }
                binding.tipSeekbar.setProgress(tipPercent)
                setTipAndTotalTextViews()
            }
        }
    }

    fun setUpTipSeekBar() {
        binding.tipSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                tipPercent = seekBar.progress
                setTipAndTotalTextViews()
                setRadioButtonAsChecked()
            }
        })

    }

    fun setRadioButtonAsChecked() {
        binding.tipRadioGroup.clearCheck()
        when (tipPercent) {
            10 -> binding.radioButtonTen.isChecked = true
            15 -> binding.radioButtonFifteen.isChecked = true
            18 -> binding.radioButtonEighteen.isChecked = true
            25 -> binding.radioButtonTwentyFive.isChecked = true
        }
    }

    fun setUpNumOfGuestsSpinner() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.num_of_guests_array, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.guestsSpinner.adapter = adapter

        binding.guestsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                numGuests = parent.getItemAtPosition(position).toString().toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    fun setTipAndTotalTextViews() {
        binding.currentTipAmount.text = "$tipPercent%"

        finalTotal = subtotal + subtotal.toDouble() * tipPercent / 100
        binding.totalWithTipAmount.text = "$%.2f".format(finalTotal)
    }

}