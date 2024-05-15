package com.example.littlelemon.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.data.MenuItem
import com.example.littlelemon.data.MyViewModel
import com.example.littlelemon.R
import com.example.littlelemon.navigation.Profile
import com.example.littlelemon.ui.theme.*

@Composable
fun Home(navController: NavHostController) {
    val viewModel: MyViewModel = viewModel()
    val databaseMenuItems by viewModel.getAllDatabaseMenuItems().observeAsState(emptyList())
    val searchPhrase = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchMenuDataIfNeeded()
    }

    Column {
        Header(navController)
        UpperPanel(searchPhrase)
        LowerPanel(databaseMenuItems, searchPhrase)
    }
}

@Composable
fun Header(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.size(5.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "LittleLemon Logo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth(0.7f)
        )

        Box(
            modifier = Modifier
                .size(50.dp)
                .clickable { navController.navigate(Profile.route) }
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Icon",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 2.dp)
            )
        }
    }
}

@Composable
fun UpperPanel(searchPhrase: MutableState<String>) {
    Column(
        modifier = Modifier
            .background(PrimaryGreen)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            text = "Little Lemon",
            style = MaterialTheme.typography.headlineLarge,
            color = PrimaryYellow
        )
        Text(text = "New York", style = MaterialTheme.typography.titleSmall, color = Color.White)
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with  a modern twist. Turkish, Italian, Indian and chinese recipes are our speciality.",
                modifier = Modifier.fillMaxWidth(0.7f),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
            )
        }

        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = searchPhrase.value,
            onValueChange = { searchPhrase.value = it },
            placeholder = { Text(text = "Enter Search Phrase") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryGreen,
                focusedLabelColor = PrimaryGreen,
                focusedContainerColor = HighlightGray,
                unfocusedContainerColor = HighlightGray
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun LowerPanel(databaseMenuItems: List<MenuItem>, searchPhrase: MutableState<String>) {
    val categories =
        databaseMenuItems.map { it.category.replaceFirstChar { char -> char.uppercase() } }.toSet()
    val selectedCategory = remember { mutableStateOf("") }

    val filteredItems = databaseMenuItems.filter {
        it.title.contains(searchPhrase.value, ignoreCase = true) &&
                (selectedCategory.value.isBlank() || it.category.equals(
                    selectedCategory.value,
                    ignoreCase = true
                ) || selectedCategory.value.equals("all", ignoreCase = true))
    }

    Column {
        MenuCategories(categories) { selectedCategory.value = it }
        Spacer(modifier = Modifier.size(2.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            filteredItems.forEach { MenuItem(it) }
        }
    }
}

@Composable
fun MenuCategories(categories: Set<String>, categoryLambda: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
            Text(text = "ORDER FOR DELIVERY", fontWeight = FontWeight.Bold)

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CategoryButton(
                    category = "All",
                    selectedCategory = { categoryLambda(it.lowercase()) })
                categories.forEach {
                    CategoryButton(
                        category = it,
                        selectedCategory = categoryLambda
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryButton(category: String, selectedCategory: (String) -> Unit) {
    Button(
        onClick = { selectedCategory(category) },
        colors = ButtonDefaults.buttonColors(
            contentColor = PrimaryGreen,
            containerColor = HighlightGray
        )
    ) {
        Text(text = category)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItem) {
    val itemDescription = if (item.description.length > 100) "${
        item.description.substring(
            0,
            100
        )
    }..." else item.description

    Card(
        modifier = Modifier.clickable { },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.fillMaxWidth(0.7f), verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(text = itemDescription, modifier = Modifier.padding(bottom = 10.dp))
                Text(text = "$ ${item.price}", fontWeight = FontWeight.Bold)
            }

            GlideImage(
                model = item.imageUrl,
                contentDescription = "",
                modifier = Modifier.size(100.dp, 100.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}
