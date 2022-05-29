package com.chernybro.wb51.presentation.hero_list_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chernybro.wb51.databinding.FragmentHeroesListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroesListFragment : Fragment() {

    private var _binding: FragmentHeroesListBinding? = null
    private val binding get() = _binding!!

    private val adapter = HeroesListAdapter()
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    private val vm: HeroesListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHeroesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            this.container.adapter = adapter
            swipeRefreshLayout = swipeRefreshView
        }
        swipeRefreshLayout?.setOnRefreshListener { vm.fetchHeroes() }

        configureObserving()
    }

    private fun configureObserving(){
        vm.items.observe(viewLifecycleOwner) { items ->
            adapter.setData(items)
            swipeRefreshLayout?.isRefreshing = false
        }

        vm.errorMessage.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}