
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.inktextil.R

@Composable
fun RegisterSocialMediaScreen(
    onEmailRegister: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo de la aplicación",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Título
        Text(
            text = "Registro con redes sociales",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botones de redes sociales
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            SocialCircleButton(R.drawable.x, "Registrar con Facebook") { /* Acción Facebook */ }
            SocialCircleButton(R.drawable.img, "Registrar con Google") { /* Acción Google */ }
            SocialCircleButton(R.drawable.img, "Registrar con Twitter") { /* Acción Twitter/X */ }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Separador con líneas y "O"
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(modifier = Modifier.weight(1f), thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
            Text(
                text = " ó ",
                modifier = Modifier.padding(horizontal = 10.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Divider(modifier = Modifier.weight(1f), thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Botón de correo electrónico
        OutlinedButton(
            onClick = { onEmailRegister },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Registrarse con correo",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Texto de iniciar sesión
        Row(
            modifier = Modifier.clickable { onLoginClick() },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¿Ya tienes cuenta? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Iniciar sesión",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

fun onLoginClick() {
    TODO("Not yet implemented")
}

@Composable
fun SocialCircleButton(imageRes: Int, description: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(MaterialTheme.colorScheme.surface, shape = CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = description,
            modifier = Modifier.size(30.dp)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewRegisterSocialMediaScreen() {
    // Usamos un NavHostController simulado, puedes cambiarlo por uno real si lo necesitas
    RegisterSocialMediaScreen(onEmailRegister = NavHostController(context = LocalContext.current))
}



