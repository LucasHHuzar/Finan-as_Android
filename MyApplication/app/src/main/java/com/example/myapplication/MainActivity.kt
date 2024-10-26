package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                LayoutMain()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutMain() {
    var usuariosNome by remember { mutableStateOf("") }
    var usuariosCpf by remember { mutableStateOf("") }
    var usuariosSaldo by remember { mutableStateOf("") }
    var usuariosList by remember { mutableStateOf<List<Usuario>>(emptyList()) }
    var mensagem by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(text = "Gerenciamento de Usuários", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para o nome do usuário
        TextField(
            value = usuariosNome,
            onValueChange = { usuariosNome = it },
            label = { Text("Nome do Usuário") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo para o cpf do usuário
        TextField(
            value = usuariosCpf,
            onValueChange = { usuariosCpf = it },
            label = { Text("CPF do Usuário") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo para o saldo do usuário
        TextField(
            value = usuariosSaldo,
            onValueChange = { usuariosSaldo = it },
            label = { Text("Saldo Inicial") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Botão para inserir usuário
        Button(onClick = {
            val saldo = usuariosSaldo.toDoubleOrNull() ?: 0.0
            addUsuarios(usuariosNome, usuariosCpf, saldo, ) // Função para inserir usuário (implementada mais abaixo)
            mensagem = "Usuário $usuariosNome adicionado!"
            usuariosNome = ""
            usuariosSaldo = ""
            loadUserList() // Atualiza a lista de usuários (implementada mais abaixo)
        }) {
            Text("Salvar Usuário")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botão para listar usuários
        Button(onClick = {
            loadUserList() // Função para carregar a lista de usuários (implementada mais abaixo)
        }) {
            Text("Listar Usuários")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mensagem de resultado
        Text(text = mensagem)

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de usuários
        LazyColumn {
            items(usuariosList) { usuario ->
                Text(text = "${usuario.nome} - Saldo: ${usuario.saldo}")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

// Simulação de inserção de usuário
private fun addUsuarios(usuariosNome: String, cpf: String, saldoInicial: Double, db: AppDatabase) {
    val novoUsuario = Usuario(nome = usuariosNome, cpf = cpf, saldo = saldoInicial)
    db.usuarioDAO().insertUsuario(novoUsuario)
}

// Simulação de carregamento de lista de usuários
suspend fun carregarUsuarios(usuariosId: Int, db: AppDatabase): Usuario? {
    return db.usuarioDAO().getUsuariosById(usuariosId)
}

suspend fun deleteUser(usuarioId: Int, db: AppDatabase) {
    db.usuarioDAO().deleteUsuarios(usuarioId)
}
