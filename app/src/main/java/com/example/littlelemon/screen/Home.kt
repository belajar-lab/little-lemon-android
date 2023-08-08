package com.example.littlelemon.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemon.Profile
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun Home(navController: NavController) {
    Column {
        Header(navController)
        HeroSection()
        //LowerPanel(navController = navController, dishes = DishRepository.dishes)
    }
}

@Composable
fun Header(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }) {

        }
        Image(
            painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth(.5f)
                .padding(horizontal = 20.dp)
        )
        IconButton(onClick = { navController.navigate(Profile.route) }) {
            Image(
                painter = painterResource(id = R.drawable.photo_profile),
                contentDescription = "Cart",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun HeroSection() {
    Column(
        modifier = Modifier
            .background(color = LittleLemonColor.green)
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.restaurant_title),
            style = MaterialTheme.typography.displayLarge,
            color = LittleLemonColor.yellow,
        )
        Text(
            text = stringResource(id = R.string.restaurant_location),
            style = MaterialTheme.typography.headlineLarge,
            color = LittleLemonColor.cloud,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.restaurant_description),
                style = MaterialTheme.typography.bodyLarge,
                color = LittleLemonColor.cloud,
                modifier = Modifier
                    .padding(bottom = 28.dp, end = 20.dp)
                    .fillMaxWidth(.6f)
            )
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Upper Panel Image",
                modifier = Modifier.clip(RoundedCornerShape(10.dp))
            )
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    HeroSection()
}