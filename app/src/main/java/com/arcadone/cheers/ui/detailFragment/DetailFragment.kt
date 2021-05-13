package com.arcadone.cheers.ui.detailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.arcadone.cheers.databinding.FragmentDetailBinding
import com.arcadone.cheers.model.BeerItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.arcadone.cheers.R
import com.google.accompanist.glide.rememberGlidePainter

class DetailFragment : BottomSheetDialogFragment() {

    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailBinding.inflate(inflater).apply {
            viewModel = detailViewModel
            lifecycleOwner = this@DetailFragment

            composeView.setContent {
                Card(args.beerItem)
            }
        }.root
    }

    @Composable
    fun Card(beerItem: BeerItem) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            Row(Modifier.fillMaxWidth()) {
                Image(
                    painter = rememberGlidePainter(
                        beerItem.imageUrl,
                        fadeIn = true,
                        previewPlaceholder = R.drawable.ic_beer
                    ),
                    contentDescription = "Description",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .fillMaxSize(0.3f),
                    alignment = Alignment.Center
                )
                Box(Modifier.weight(4f))
                {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            beerItem.name,
                            style = MaterialTheme.typography.h5,
                            color = Color.White
                        )
                        Text(
                            beerItem.tagline,
                            style = MaterialTheme.typography.h6,
                            color = Color.White
                        )
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = "Bookmark",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth(0.1f)
                        .weight(1f),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Text(
                    beerItem.description,
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
            }
        }
    }

    @Composable
    @Preview
    fun FlexibleComposable() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_beer),
                    contentDescription = "Description",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .size(120.dp)
                        .background(Color.DarkGray),
                    alignment = Alignment.TopStart
                )
                Box(Modifier.weight(4f))
                {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            "name",
                            style = MaterialTheme.typography.h5,
                            color = Color.White
                        )
                        Text(
                            "tagline",
                            style = MaterialTheme.typography.h6,
                            color = Color.White
                        )
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = "Bookmark",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth(0.1f)
                        .weight(1f),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Text(
                    "description",
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
            }
        }

    }
}