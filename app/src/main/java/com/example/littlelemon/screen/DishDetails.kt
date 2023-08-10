package com.example.littlelemon.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.DishRepository
import com.example.littlelemon.R

@Composable
fun DishDetails(id: Int) {
    val dish = requireNotNull(DishRepository.getDish(id))
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Image(
            painter = painterResource(id = dish.imageResource),
            contentDescription = "Dish Image",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
            Text(text = dish.name, style = MaterialTheme.typography.titleLarge)
            Text(text = dish.description, style = MaterialTheme.typography.bodyLarge)
            Counter()
            Button(onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(id = R.string.add_for) + " $${dish.price}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun Counter() {
    var counter by remember {
        mutableStateOf(1)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = { counter-- }) {
            Text(text = "-", style = MaterialTheme.typography.headlineMedium)
        }
        Text(
            text = counter.toString(),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        TextButton(onClick = { counter++ }) {
            Text(text = "+", style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DishDetailsPreview() {
    DishDetails(id = 5)
}