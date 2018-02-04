package com.gimenez.kent.spotify

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.SparseBooleanArray
import android.view.View
import android.widget.Toast
import com.gimenez.kent.spotify.model.SongModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_song.*

class MainActivity : AppCompatActivity() {

    private var mSongList: ArrayList<SongModel> = ArrayList()
    private var mAdapter: SongAdapter? = null
    private var mSelected: SparseBooleanArray = SparseBooleanArray()

    companion object {
        const val PERMISSION_REQ_CODE = 1111
        const val KEY = "asdad"
    }

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQ_CODE)
        } else {
            loadData()
        }
    }

    private fun loadData() {
        val songCursor: Cursor? = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    null, null, null, null)

        while (songCursor != null && songCursor.moveToNext()) {
            val songName = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val songArtist = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val songAlbum = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
            val songPath = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA))

            mSongList.add(SongModel(songName, songArtist, songAlbum,songPath))
        }

        mAdapter = SongAdapter(mSelected,mSongList)
        val layoutManager = LinearLayoutManager(this)
            recyclerView1.layoutManager = layoutManager
            recyclerView1.itemAnimator = DefaultItemAnimator()
            recyclerView1.adapter = mAdapter

            recyclerView1.addOnItemTouchListener(RecyclerTouchListener(this, object : RecyclerTouchListener.ClickListener {


                override fun onClick(view: View, position: Int) {
                     val fragmentContainer = containerFragment
                     val musicFragment = SongFragment()
                     val bundle = Bundle()

                    bundle.putParcelable(KEY, mSongList[position])
                    musicFragment.arguments = bundle
                    supportFragmentManager.beginTransaction().replace(R.id.containerFragment,musicFragment).commit()
                    view.isSelected = true
                    fragmentContainer.visibility =  View.VISIBLE

                }
            }))
        }

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == PERMISSION_REQ_CODE) {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(applicationContext, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show()
                    loadData()
                }
            }
        }
    }









            /*mSongs = ArrayList()
            mAdapter = SongsAdapter(mSongs)
            recyclerViewInit()
}

        mSongs.add(Songs("Back at one" ,"Brian Mcknight", "None"))
        mSongs.add(Songs("Before I let you go", "Side A & Freestyle" , "Back to Back"))
        mSongs.add(Songs("Versace on the floor", "Bruno Mars" , "24k Magic"))
        mSongs.add(Songs("Attention" , "Charlie Puth" , "Attention"))
        mSongs.add(Songs("Young dum and Broke", "Khalid", "American Teen"))
        mSongs.add(Songs("Dahil Sayo" , "Inigo Pascual" , "Inigo Pascual"))
        mSongs.add(Songs("Back at one" ,"Brian Mcknight", "None"))
        mSongs.add(Songs("Before I let you go", "Side A & Freestyle" , "Back to Back"))
        mSongs.add(Songs("Versace on the floor", "Bruno Mars" , "24k Magic"))
        mSongs.add(Songs("Back at one" ,"Brian Mcknight", "None"))
        mSongs.add(Songs("Before I let you go", "Side A & Freestyle" , "Back to Back"))
        mSongs.add(Songs("Versace on the floor", "Bruno Mars" , "24k Magic"))
        mSongs.add(Songs("Back at one" ,"Brian Mcknight", "None"))
        mSongs.add(Songs("Before I let you go", "Side A & Freestyle" , "Back to Back"))
        mSongs.add(Songs("Versace on the floor", "Bruno Mars" , "24k Magic"))*/



   /* private fun recyclerViewInit() {
        recyclerView1.layoutManager = LinearLayoutManager(this)
        recyclerView1.setHasFixedSize(true)
        recyclerView1.adapter = mAdapter
        recyclerView1.itemAnimator = DefaultItemAnimator()
    }


    override fun onSaveInstanceState (outState: Bundle?){
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList("data",mSongs)

    }*/


