package com.pointlessapps.mypremiummobile.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.pointlessapps.mypremiummobile.R
import com.pointlessapps.mypremiummobile.compose.model.UserInfo

@Composable
internal fun TopBar(
    userInfo: UserInfo,
    onSettingsClicked: (() -> Unit)? = null,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background,
        elevation = dimensionResource(id = R.dimen.default_elevation),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(dimensionResource(id = R.dimen.medium_padding)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.icon_button_size))
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.surface),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size)),
                    painter = painterResource(id = R.drawable.ic_person),
                    tint = colorResource(id = R.color.accent),
                    contentDescription = null,
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.tiny_padding)),
            ) {
                ComposeText(
                    text = userInfo.name,
                    textStyle = defaultComposeTextStyle().copy(
                        textColor = MaterialTheme.colors.onBackground,
                    ),
                )
                ComposeText(
                    text = userInfo.phoneNumber.orEmpty(),
                    textStyle = defaultComposeTextStyle().copy(
                        textColor = colorResource(id = R.color.grey),
                    ),
                )
            }
            if (onSettingsClicked != null) {
                Box(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.icon_button_size))
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.surface)
                        .clickable(onClick = onSettingsClicked),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size)),
                        painter = painterResource(id = R.drawable.ic_settings),
                        tint = colorResource(id = R.color.accent),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
