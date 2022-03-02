package com.example.breakingbadproject.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbadproject.databinding.CharacterItemBinding
import com.example.breakingbadproject.model.CharactersModelItem
import com.example.breakingbadproject.util.Constants.Companion.CHARACTER_UUID
import com.example.breakingbadproject.util.displayImage
import com.example.breakingbadproject.util.makePlaceholder
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.collections.ArrayList

class CharactersAdapter(private var charList : ArrayList<CharactersModelItem>) : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    // Without viewBinding
    // class CharacterViewHolder(view: View ) : RecyclerView.ViewHolder(view) {
    class CharacterViewHolder(binding: CharacterItemBinding ) : RecyclerView.ViewHolder(binding.root) {
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

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
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
}