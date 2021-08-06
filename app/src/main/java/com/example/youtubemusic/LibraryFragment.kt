package com.example.youtubemusic

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ListView
import android.widget.Toast
import com.example.youtubemusic.databinding.FragmentLibraryBinding

data class Playlists(val name:String, val info:String, val image:Int)

class LibraryFragment : Fragment(){
    private var mBinding: FragmentLibraryBinding? =null
    private val  binding  get()  = mBinding!!
    private val playlist1Fragment  = Playlist1Fragment()
    private lateinit var playListPrefs: SharedPreferences
    private lateinit var editor:SharedPreferences.Editor

    //리스트뷰
    private lateinit var playlistsAdapter: PlaylistsAdapter

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentLibraryBinding.inflate(inflater,  container, false)

//        playlistArrayList.add(
//            Playlists("My Playlist", "Soyeon Kim • 20 songs", R.drawable.what_would_you_do))

        var listData = arrayListOf<Playlists>(
            Playlists("My Playlist", "Soyeon Kim • 20 songs", R.drawable.what_would_you_do),
            Playlists("My Playlist 2", "Soyeon Kim • 52 songs", R.drawable.comethru)
        )
        //리스트뷰
        playlistsAdapter = PlaylistsAdapter(requireContext(), listData)
        binding.listviewPlaylists.adapter  = playlistsAdapter

//        val bundle  =  this.arguments
//        if (bundle != null) {
//            val  plName  =  bundle.getString("name",  "(playlist name)")
//            binding.playlistPreviousName.setText(plName)      //text  바꾸기
//            position = bundle.getInt("position",  0)
//        }


        //CREATE  NEW  PLAYLIST  DIALOG  띄우기
        binding.addPlaylist.setOnClickListener{
            val createPlaylistDialog  = CreatePlaylistDialog(requireContext())
            createPlaylistDialog.showDialog()

            //CREATE NEW PLAYLIST_다이얼로그에서  정의한  인터페이스를  통해 입력한 데이터 받기
            createPlaylistDialog.setOnCreateBtnClickListener(object :CreatePlaylistDialog.CreateBtnClickListener {
                override fun onClick(playlistTitle: String) {
                    //Log.d("log", playlistArrayList.toString())  []
                    if(playlistTitle.isNotEmpty()){
                        Toast.makeText(context,  playlistTitle, Toast.LENGTH_SHORT).show()
                        listData.add(Playlists(playlistTitle,  "Soyeon Kim • 0 song",  R.drawable.default_album_image))
                        playlistsAdapter.notifyDataSetChanged()
                    }
                }
            })
        }


        //각  플레이리스트 (리스트뷰 아이템) 클릭  시    --  (이거 onCreate에  쓰면  NPE)
        binding.listviewPlaylists.setOnItemClickListener { parent, view, position, id ->
            val selectedList  = parent.getItemAtPosition(position) as Playlists
            Toast.makeText(requireContext(), selectedList.name, Toast.LENGTH_SHORT).show()  //WORKS!!!
            //if(position==0){
                //다른  프래그먼트로 이동하는 코드  +  back 버튼 설정
                Log.d("log", "onCreateView: item clicked")    //WORKS!!!
                childFragmentManager.beginTransaction().apply {
                    replace(R.id.library_fragment_container, playlist1Fragment)
                    commit()
                }
            //}
        }


        Log.d("log", "라이브러리 프래그먼트 ${listData[0].name}")
        //시도
//        val bundle = Bundle()
//        val dataArrayList:ArrayList<String>
//        //dataArrayList.add(0,  playlistArrayList[0].toString())
//        val playlistName  =  listData[0]
//        //val playlistInfo  =  playlistArrayList[1]
//        bundle.putString("plName",  playlistName.toString())
//        //("info", playlistName.toString(), playlistInfo.toString())
//
//        LibraryFragment().arguments  = bundle
//        parentFragmentManager.beginTransaction().replace(bottom    R.id.playlist_name,  )

        return binding.root

    }

    override fun onPause() {
        super.onPause()
        savePrefs()
    }

    private fun savePrefs(){
        //playListPrefs = this.activity?.getSharedPreferences("playlistsPrefs",0) ?:
    }

//    interface PlaylistInfoListener {
//        fun onMenuClick(name: String,  info: String)
//    }
//    private lateinit var onMenuClickListener: PlaylistInfoListener
//
//    fun setOnMenuClickListener(listener: PlaylistInfoListener){
//        onMenuClickListener  = listener
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.d("log", "onCreate")
    }



    override fun onDestroyView() {
        mBinding  =  null
        super.onDestroyView()
        //arraylist 저장 : arraylist는 json으로 바꿔서 저장

//        editor.clear()
//        editor.commit()
        Log.d("log", "library  frag  onDestroyView")
    }
}
