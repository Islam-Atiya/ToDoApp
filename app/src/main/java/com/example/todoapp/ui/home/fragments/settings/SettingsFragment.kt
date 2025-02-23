package com.example.todo.ui.home.fragments.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSettingsBinding
import com.example.todoapp.ui.util.applyModeChange
import com.example.todoapp.ui.util.constans
import org.intellij.lang.annotations.Language

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        sharedPreferences =
            requireContext().getSharedPreferences(constans.sp_Name, Context.MODE_PRIVATE)
    }

    override fun onStart() {
        super.onStart()
        intiUI()

        setLanguageDropDownMenu()
        setModeDropDownMenu()
    }

    private fun setModeDropDownMenu() {

        val mode = resources.getStringArray(R.array.modes).toList()
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, mode)
        binding.autoCompleteTVModes.setAdapter(adapter)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setLanguageDropDownMenu() {

        val languages = resources.getStringArray(R.array.languages).toList()
        val adapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, languages)
        binding.autoCompleteTVLanguages.setAdapter(adapter)
    }

    private fun setupListener() {
        setLanguageDropDownMenuListener()
        setModeDropDownMenuListener()

    }

    private fun setModeDropDownMenuListener() {
        binding.autoCompleteTVModes.setOnItemClickListener { _, _, position, _ ->
            val selectedMode = binding.autoCompleteTVModes.adapter.getItem(position).toString()

            binding.autoCompleteTVModes.setText(selectedMode)

            val isDark = selectedMode == getString(R.string.dark)
            applyModeChange(isDark)
            saveModeToSp(isDark)
        }
    }

    private fun saveModeToSp(isdark: Boolean) {

        val editor = sharedPreferences.edit()
        editor.putBoolean(constans.isDarkModeKey, isdark)
        editor.apply()
    }


    @SuppressLint("SuspiciousIndentation")
    private fun setLanguageDropDownMenuListener() {
        binding.autoCompleteTVLanguages.setOnItemClickListener { _, _, position, _ ->
            val selectedLanguage =
                binding.autoCompleteTVLanguages.adapter.getItem(position).toString()
            binding.autoCompleteTVLanguages.setText(selectedLanguage)

            val languageCod = when (selectedLanguage) {
                getString(R.string.english) -> constans.engCode
                getString(R.string.arabic) -> constans.arCode
                else ->
                    constans.engCode
            }

            applyLanguageChange(languageCod)
        }
    }

    private fun applyLanguageChange(languageCode: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
    }


    private fun intiUI() {
        setInitialLanguageState()
        setInitialModeState()
    }

    private fun setInitialModeState() {
        val currentDeviceMode = AppCompatDelegate.getDefaultNightMode()
        val mode = when (currentDeviceMode) {
            AppCompatDelegate.MODE_NIGHT_YES -> R.string.dark
            else -> R.string.light

        }
        binding.autoCompleteTVModes.setText(mode)
    }


    private fun setInitialLanguageState() {

        val currentLanguageCode =
            AppCompatDelegate.getApplicationLocales()[0]?.language ?: getCurrentDeviceLanguageCode()
        val language = when (currentLanguageCode) {
            constans.engCode -> R.string.english
            constans.arCode -> R.string.arabic
            else -> R.string.english
        }
        binding.autoCompleteTVLanguages.setText(language)
    }

    private fun getCurrentDeviceLanguageCode(): String {

        return resources.configuration.locales[0].language
    }
}


