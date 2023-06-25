package com.example.littlelemon

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun LowerPanel(navController: NavController, dishes: List<Dish> = listOf()) {
    WeeklySpecialCard()
    LazyColumn {
        itemsIndexed(dishes) { _, dish ->
            MenuDish(navController = navController, dish = dish)
        }
    }
}

@Composable
fun WeeklySpecialCard() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.weekly_special),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDish(navController: NavController? = null, dish: Dish) {
    Card(
        onClick = {
            Log.d("AAA", "Click ${dish.id}")
            navController?.navigate(DishDetails.route + "/${dish.id}")
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = dish.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = dish.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth(.75f)
                        .padding(top = 5.dp, bottom = 5.dp)
                )
                Text(
                    text = "$${dish.price}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Image(
                painter = painterResource(id = dish.imageResource),
                contentDescription = "Dish Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.aspectRatio(1f/1f).clip(RoundedCornerShape(10.dp))
            )
        }
    }
    Divider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        color = LittleLemonColor.yellow,
        thickness = 1.dp
    )
}