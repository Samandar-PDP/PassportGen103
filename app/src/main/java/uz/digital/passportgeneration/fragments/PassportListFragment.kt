package uz.digital.passportgeneration.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.digital.passportgeneration.MainActivity
import uz.digital.passportgeneration.R
import uz.digital.passportgeneration.adapter.PassportAdapter
import uz.digital.passportgeneration.database.PassportDatabase
import uz.digital.passportgeneration.databinding.FragmentPassportListBinding

class PassportListFragment : Fragment() {

    private var _binding: FragmentPassportListBinding? = null
    private val binding get() = _binding!!
    private val passportAdapter by lazy { PassportAdapter() }
    private val passportDatabase by lazy { PassportDatabase(requireContext()) }

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
                val list = database.getPassports().toMutableList().filter {
                    it.name.lowercase().contains(newText!!) ||
                            it.lastName.lowercase().contains(newText)
                }
                passportAdapter.filter(list.toMutableList())
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_passportListFragment_to_addEditFragment)
        }
        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = passportAdapter
        }
        passportAdapter.passportList = database.getPassports().toMutableList()
        passportAdapter.onClick = {
            val bundle = bundleOf("passport" to it)
            findNavController().navigate(R.id.action_passportListFragment_to_detailFragment, bundle)
        }
        passportAdapter.onEdit = {
            val bundle = bundleOf("passport" to it)
            findNavController().navigate(
                R.id.action_passportListFragment_to_addEditFragment,
                bundle
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}