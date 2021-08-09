package com.example.youtubemusic

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.youtubemusic.databinding.CreatePlaylistDialogBinding

class CreatePlaylistDialog(context: Context) {
    private val createPlaylistDialog  =  Dialog(context)
    //private lateinit var binding : CreatePlaylistDialogBinding


    //private var addPlaylistDialogInterface: AddPlaylistDialogInterface? = null
//    init {    //인터페이스와  연결
//        this.addPlaylistDialogInterface= addPlaylistDialogInterface
//    }

    fun showDialog(){
        createPlaylistDialog.setContentView(R.layout.create_playlist_dialog)   //다이얼로그에서 사용할 레이아웃
        createPlaylistDialog.setCancelable(true)
        createPlaylistDialog.setCanceledOnTouchOutside(false)
        createPlaylistDialog.show()
        createPlaylistDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)    //키보드  안됨

        val editText = createPlaylistDialog.findViewById<EditText>(R.id.edit_text)
        val cancelBtn =  createPlaylistDialog.findViewById<Button>(R.id.cancel_btn)
        val createBtn =  createPlaylistDialog.findViewById<Button>(R.id.create_btn)

        createBtn.isClickable = false
        createBtn.isEnabled = false

        //create button  활성화/비활성화
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(editText.length()>0){
                    createBtn.isClickable = true
                    createBtn.isEnabled = true
                    createBtn.setTextColor(Color.WHITE)
                    editText.setTextColor(Color.WHITE)
                }  else  {
                    createBtn.isClickable = false
                    createBtn.isEnabled = false
                    createBtn.setTextColor(Color.parseColor("#4C4C4C"))
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }

        })


        cancelBtn.setOnClickListener{
            createPlaylistDialog.dismiss()
        }
        createBtn.setOnClickListener{
            onCreateBtnClickListener.onClick(editText.text.toString()) //인터페이스 통해 데이터 전달
            createPlaylistDialog.dismiss()
        }

    }

    //데이터 전달 관련  library  fragment 로 전달
    interface CreateBtnClickListener {
        fun  onClick(playlistTitle :  String)
    }
    private lateinit var onCreateBtnClickListener: CreateBtnClickListener

    fun setOnCreateBtnClickListener(listener:  CreateBtnClickListener){
        onCreateBtnClickListener  = listener
    }




//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding  =  CreatePlaylistDialogBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        //배경 투명하게
//        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        //둘 다  잘됨!!!  -- 이게 onclick함수에서  작동하도록  만들어야  함  + fragment에서 데이터필요하다면
//        binding.createBtn.setOnClickListener {
//            val textResult  =  binding.editText.text.toString()
//            if(textResult.isNotEmpty()){
//                //binding.createBtn.getSolidColor("000000")  //글자색바꾸기
//                Toast.makeText(context,  "$textResult", Toast.LENGTH_SHORT).show()
//
//            }
//            //Log.d("log", "dialog  -  create  버튼  클릭")
//            dismiss()
//        }
//
//        binding.cancelBtn.setOnClickListener{
//            dismiss()
//        }
//
//        onClick(currentFocus)
//    }
//
//    //이건  기능  안함,,,
//    override fun onClick(v: View?) {
//        when(v){
//            binding.createBtn  -> {
//                val textResult  =  binding.editText.text.toString()
//                Toast.makeText(context,  "$textResult", Toast.LENGTH_SHORT).show()
//                Log.d("log", "dialog  -  create  버튼  클릭")
//                dismiss()
//            }
//            binding.cancelBtn -> {
//                Log.d("log", "dialog  -  cancel  버튼  클릭")
//                this.addPlaylistDialogInterface?.onCancelBtnClick()
//            }
//        }
//    }





}