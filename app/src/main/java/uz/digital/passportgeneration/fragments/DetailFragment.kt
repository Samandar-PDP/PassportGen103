package uz.digital.passportgeneration.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.digital.passportgeneration.R
import uz.digital.passportgeneration.database.PassportDatabase
import uz.digital.passportgeneration.databinding.FragmentDetailBinding
import uz.digital.passportgeneration.model.Passport
import uz.digital.passportgeneration.util.snackBar
import uz.digital.passportgeneration.util.toImage

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var passport: Passport? = null
    private val database by lazy { PassportDatabase(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        passport = arguments?.getParcelable("passport") as? Passport
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passport?.let {
            binding.imageView2.setImageBitmap(it.image.toImage())
            binding.textView.text = "${it.name}${it.lastName}${it.fatName}"
            binding.button.setOnClickListener { v ->
                database.deletePassport(it.id)
                snackBar("Deleted")
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}