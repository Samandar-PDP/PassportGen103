package uz.digital.passportgeneration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import uz.digital.passportgeneration.database.PassportDatabase
import uz.digital.passportgeneration.databinding.FragmentAddEditBinding
import uz.digital.passportgeneration.model.Passport
import uz.digital.passportgeneration.util.snackBar
import uz.digital.passportgeneration.util.toByteArray

class AddEditFragment : Fragment() {

    private var _binding: FragmentAddEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var region: String
    private lateinit var gender: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = PassportDatabase(requireContext())
        binding.btnSaveEdit.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val lastname = binding.lastname.text.toString().trim()
            val fatName = binding.faName.text.toString().trim()
            val city = binding.city.text.toString().trim()
            val address = binding.address.text.toString().trim()
            val gotDate = binding.getDate.text.toString().trim()
            val lifeTime = binding.lifeTime.text.toString().trim()
            database.savePassport(
                Passport(
                    name = name,
                    lastName = lastname,
                    fatName = fatName,
                    region = region,
                    city = city,
                    address = address,
                    gotDate = gotDate,
                    lifeTime = lifeTime,
                    gender = gender,
                    image = binding.imageView.toByteArray()
                )
            )
            snackBar("Saved")
        }
        binding.imageView.setOnClickListener {
            launcher.launch("image/*")
        }
        val regions = arrayOf("Andijon", "Farg'ona", "Namangan", "Toshkent")
        val genders = arrayOf("Male", "Female", "Unknown")
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, regions)
        val arrayAdapter2 =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, genders)
        binding.region.setAdapter(arrayAdapter)
        binding.gender.setAdapter(arrayAdapter2)
        binding.region.setOnItemClickListener { parent, view, position, id ->
            region = regions[position]
        }
        binding.gender.setOnItemClickListener { parent, view, position, id ->
            gender = genders[position]
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.imageView.setImageURI(it)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}