package com.tuevento.generador.infraestructure.web;

import com.tuevento.generador.application.dto.landing.RenderLandingPageViewModel;
import com.tuevento.generador.application.usecase.landing.RenderLandingPageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Controller MVC para renderizar la landing page con Thymeleaf.
 */
@Controller
@RequiredArgsConstructor
public class LandingRenderController {

    private final RenderLandingPageUseCase renderUseCase;

    /**
     * Sirve la landing page renderizada.
     * @param id    ID de la landing page
     * @param model Model de Thymeleaf para inyectar datos y fragmentos
     * @return      nombre del template Thymeleaf
     */
    @GetMapping("/landing/{id}")
    public String renderLanding(@PathVariable Long id, Model model) {
        RenderLandingPageViewModel vm = renderUseCase.execute(id);
        model.addAttribute("vm", vm);

        // Agregar fragmentos dinámicos según flags
        if (vm.getLanding().isShowMap()) {
            model.addAttribute("mapFragment", "fragments/map :: map");
        }
        if (vm.getLanding().isShowSchedule()) {
            model.addAttribute("scheduleFragment", "fragments/schedule :: schedule");
        }
        if (vm.getLanding().isShowSpeakers()) {
            model.addAttribute("speakersFragment", "fragments/speakers :: speakers");
        }

        return "landing/base";
    }
}
