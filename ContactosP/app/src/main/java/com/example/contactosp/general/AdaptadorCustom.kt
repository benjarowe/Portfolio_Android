package com.example.contactosp.general

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.contactosp.R

class AdaptadorCustom(private val contexto: Context, private var items: ArrayList<Contacto>) : BaseAdapter() {

    private var copiaItems: ArrayList<Contacto> = ArrayList(items)

    // Devuelve el número de elementos en la lista de contactos
    override fun getCount(): Int {
        return items.size
    }

    // Devuelve el objeto en la posición especificada en la lista de contactos
    override fun getItem(position: Int): Any {
        return items[position]
    }

    // Devuelve el ID de un elemento en la posición especificada en la lista de contactos
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Infla y devuelve la vista de cada elemento en la lista de contactos
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder
        var vista: View? = convertView

        // Si la vista es nula, se infla utilizando el LayoutInflater
        if (vista == null) {
            vista = LayoutInflater.from(contexto).inflate(R.layout.template_contacto, parent, false)
            viewHolder = ViewHolder(vista)
            vista.tag = viewHolder
        } else {
            viewHolder = vista.tag as ViewHolder
        }

        // Se obtiene el objeto Contacto correspondiente a la posición actual
        val item = getItem(position) as Contacto

        with(viewHolder) {
            // Se asignan los valores del Contacto a las vistas correspondientes en el ViewHolder
            nombre.text = item.nombre
            empresa.text = item.empresa

            // Carga la imagen utilizando Glide
            Glide.with(contexto)
                .load(Uri.parse(item.fotoUri))
                .placeholder(R.drawable.foto_01) // Imagen predeterminada mientras se carga
                .error(R.drawable.foto_02) // Imagen en caso de error
                .into(foto)
        }
        return vista!!
    }

    fun filtrar(str: String) {
        items.clear()
        if (str.isEmpty()) {
            items.addAll(copiaItems) // Si la cadena está vacía, restaura la lista original
        } else {
            val busqueda = str.toLowerCase()
            items.addAll(copiaItems.filter { it.nombre.toLowerCase().contains(busqueda) })
        }
        notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
    }

    //ViewHolder para contener las vistas de cada elemento en la lista
    private class ViewHolder(vista: View) {
        var nombre: TextView = vista.findViewById(R.id.tvNombre)
        var foto: ImageView = vista.findViewById(R.id.ivFoto)
        var empresa: TextView = vista.findViewById(R.id.tvEmpresa)
    }
}
