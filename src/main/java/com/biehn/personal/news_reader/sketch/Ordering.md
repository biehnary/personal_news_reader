application.yml
news
sources
→ id: SourceId
→ display-order: int
→ section: Section

    sections
      → section: Section
      → display-order: int

NewsProperties
→ sources: List<SourceConfig>
→ sections: List<SectionConfig>

SourceConfig
→ id: SourceId
→ displayOrder: int
→ section: Section

SectionConfig
→ section: Section
→ displayOrder: int


Section
→ NEWS
→ ECONOMY


SectionViewModel
→ section: Section
→ newsSourceViewModels: List<NewsSourceViewModel>

NewsSourceViewModel
→ sourceName: String
→ newsItems: List<NewsItem>


Section Ordering
SectionViewModel.section
→ Section
→ SectionConfig
→ displayOrder