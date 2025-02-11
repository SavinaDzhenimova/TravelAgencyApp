package org.travelagency.model.exportDTO;

import java.util.List;

public class ExcursionViewInfo {

    List<ExcursionViewDTO> excursions;

    public ExcursionViewInfo(List<ExcursionViewDTO> excursions) {
        this.excursions = excursions;
    }

    public List<ExcursionViewDTO> getExcursions() {
        return excursions;
    }

    public void setExcursions(List<ExcursionViewDTO> excursions) {
        this.excursions = excursions;
    }
}
