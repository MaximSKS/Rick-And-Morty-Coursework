package com.example.rickandmortycoursework.util


import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmortycoursework.R
import com.example.rickandmortycoursework.domain.model.CharactersDomain
import com.example.rickandmortycoursework.domain.model.EpisodeDomain
import com.example.rickandmortycoursework.presentation.episode.adapter.EpisodeAdapter
import com.example.rickandmortycoursework.presentation.location.adapter.LocationDetailAdapter

@BindingAdapter("imageUrl")
fun downloadImage(imageView: ImageView, url: String?) {

    url?.let {
        imageView.load(url) {
            crossfade(true)
                .error(R.drawable.error_image)
                .placeholder(R.drawable.animate_loading)
        }

    }
}

@BindingAdapter("bindEpisodeList")
fun bindEpisodeList(recyclerView: RecyclerView, episodeList: List<EpisodeDomain>?) {

    if (!episodeList.isNullOrEmpty()) {

        val adapter = recyclerView.adapter as EpisodeAdapter
        adapter.submitList(episodeList)
    }
}

@BindingAdapter("isVisible")
fun isFilter(view: View, isFilter: Boolean) {
    view.visibility = if (isFilter) View.VISIBLE else View.GONE
}

@BindingAdapter("isFilter")
fun isFilter(textView: TextView, checkIsFilter: () -> Boolean) {

    textView.visibility = if (checkIsFilter.invoke()) View.VISIBLE else View.GONE
}


@BindingAdapter("bindCharacterList")
fun bindCharactersList(recyclerView: RecyclerView, characters: List<CharactersDomain>?) {

    if (!characters.isNullOrEmpty()) {

        val adapter = recyclerView.adapter as LocationDetailAdapter
        adapter.submitList(characters)
    }
}

@BindingAdapter("isLoading")
fun isLoading(progressBar: ProgressBar, isLoading: Boolean) {

    if (isLoading) {
        progressBar.visibility = View.VISIBLE
    } else {
        progressBar.visibility = View.GONE
    }
}


// Determine the colors as per status of the characters.

@BindingAdapter("statusColor")
fun changeColor(card: CardView, status: String) {

    when (status) {
        "Dead" -> card.setCardBackgroundColor(Color.RED)
        "Alive" -> card.setCardBackgroundColor(Color.GREEN)
        else -> card.setCardBackgroundColor(Color.GRAY)
    }
}

@BindingAdapter("statusColor")
fun changeColor(textView: TextView, status: String) {

    when (status) {
        "Dead" -> textView.setTextColor(Color.RED)
        "Alive" -> textView.setTextColor(Color.GREEN)
        else -> textView.setTextColor(Color.GRAY)
    }

}


