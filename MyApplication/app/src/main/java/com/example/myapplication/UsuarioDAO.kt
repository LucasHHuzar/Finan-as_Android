package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDAO {

    @Insert
    suspend fun insertUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE nome = :usuariosId")
    suspend fun getUsuariosById(usuariosId: Int): Usuario?

    @Query("UPDATE usuarios SET saldo = :novoSaldo WHERE id = :usuariosId")
    suspend fun updateUsuariosSaldo(usuariosId: Int, novoSaldo: Double)

    @Query("DELETE FROM usuarios WHERE id = :usuariosId")
    suspend fun deleteUsuarios(usuariosId: Int)

}
