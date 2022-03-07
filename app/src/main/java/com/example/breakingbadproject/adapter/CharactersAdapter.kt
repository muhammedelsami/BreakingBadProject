package com.example.breakingbadproject.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbadproject.database.CharactersDatabase
import com.example.breakingbadproject.databinding.CharacterItemBinding
import com.example.breakingbadproject.model.CharactersModelItem
import com.example.breakingbadproject.ui.DetailActivity
import com.example.breakingbadproject.util.Constants.Companion.CHARACTER_UUID
import com.example.breakingbadproject.viewmodel.CharacterViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class CharactersAdapter(private var charList : ArrayList<CharactersModelItem>) : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>(), CharacterClickListener {

    // Without viewBinding
    // class CharacterViewHolder(view: View ) : RecyclerView.ViewHolder(view) {
    class CharacterViewHolder(var binding: CharacterItemBinding ) : RecyclerView.ViewHolder(binding.root) {
        val name : TextView = binding.characterName
        val img : CircleImageView = binding.characterImage
        val birthday : TextView = binding.characterBirthday

        init {
            // Without viewBinding
            // name = view.findViewById(R.id.characterName)
            // img = view.findViewById(R.id.characterImage)
            // birthday = view.findViewById(R.id.characterBirthday)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = CharacterItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CharacterViewHolder(view)
        // Without viewBinding
        // return CharacterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_item,parent,false))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        // dataBinding
        holder.binding.characters = charList[position]
        holder.binding.listener = this
        holder.binding.characterDelete.setOnClickListener {
            val delete = CharacterViewModel(holder.itemView.context.applicationContext as Application)
            delete.deleteCharacter(holder.binding.characterUuid.text.toString().toInt()).apply {
                charList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,charList.size)
            }

        }

        /*
        // Without dataBinding
        holder.name.text = charList[position].name
        holder.birthday.text = charList[position].birthday
        //holder.img.setImageResource(charList.get(position).img)

        //Picasso.get().load(charList[position].img).placeholder(R.drawable.ic_launcher_foreground).into(holder.img)
        holder.img.displayImage(charList[position].img, makePlaceholder(holder.img.context))
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(CHARACTER_UUID, charList[position].uuid)
            holder.itemView.context.startActivity(intent)
        }
        */

    }

    override fun getItemCount(): Int {
        return charList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list : ArrayList<CharactersModelItem>) {
        charList.clear()
        charList = list
        notifyDataSetChanged()
    }



    override fun characterClicked(view: View, ch : CharactersModelItem) {
        Log.d("test", "characterClicked: "+view.id.toString())
        //view.context

        val intent = Intent(view.context, DetailActivity::class.java)
        intent.putExtra(CHARACTER_UUID, ch.uuid)
        view.context.startActivity(intent)
    }
}