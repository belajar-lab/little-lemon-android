package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun LowerPanel(dishes: List<Dish> = listOf()) {
    WeeklySpecialCard()
    LazyColumn {
        itemsIndexed(dishes) { _, dish ->
            MenuDish(dish = dish)
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

@Composable
fun MenuDish(dish: Dish) {
    Card {
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
                modifier = Modifier.clip(RoundedCornerShape(10.dp))
            )
        }
    }
    Divider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        color = LittleLemonColor.yellow,
        thickness = 1.dp
    )
}