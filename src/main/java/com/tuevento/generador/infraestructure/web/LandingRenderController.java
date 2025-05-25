package com.tuevento.generador.infraestructure.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tuevento.generador.application.dto.landing.RenderLandingPageViewModel;
import com.tuevento.generador.application.usecase.landing.RenderLandingPageUseCase;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LandingRenderController {

    private final RenderLandingPageUseCase renderUseCase;

    /**
     * Sirve la landing page renderizada con Thymeleaf.
     */
    @GetMapping("/landing/{id}")
    public String renderLanding(@PathVariable Long id, Model model) {
        RenderLandingPageViewModel vm = renderUseCase.execute(id);
        model.addAttribute("vm", vm);
        return "landing/base"; // apunte a tu template Thymeleaf en src/main/resources/templates/landing/base.html
    }
}