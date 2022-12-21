package uz.digital.passportgeneration.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import uz.digital.passportgeneration.MainActivity
import uz.digital.passportgeneration.R
import uz.digital.passportgeneration.database.PassportDatabase
import uz.digital.passportgeneration.databinding.FragmentPassportListBinding

class PassportListFragment : Fragment() {

    private var _binding: FragmentPassportListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).supportActionBar?.show()
        _binding = FragmentPassportListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = PassportDatabase(requireContext())
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider, SearchView.OnQueryTextListener {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val searchItem = menu.findItem(R.id.searchView)
                val searchView = searchItem.actionView as SearchView
                searchView.setOnQueryTextListener(this)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("@@@", "onQueryTextChange: $newText")
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_passportListFragment_to_addEditFragment)
        }
        Log.d("@@@", "onViewCreated: ${database.getPassports()}")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}