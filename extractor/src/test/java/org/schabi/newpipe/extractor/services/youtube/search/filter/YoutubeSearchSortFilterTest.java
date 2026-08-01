package org.schabi.newpipe.extractor.services.youtube.search.filter;

import org.junit.jupiter.api.Test;
import org.schabi.newpipe.extractor.services.youtube.search.filter.protobuf.DateFilter;
import org.schabi.newpipe.extractor.services.youtube.search.filter.protobuf.Features;
import org.schabi.newpipe.extractor.services.youtube.search.filter.protobuf.LenFilter;
import org.schabi.newpipe.extractor.services.youtube.search.filter.protobuf.SearchRequest;
import org.schabi.newpipe.extractor.services.youtube.search.filter.protobuf.SortOrder;
import org.schabi.newpipe.extractor.services.youtube.search.filter.protobuf.TypeFilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class YoutubeSearchSortFilterTest {
    @Test
    void encodesAndDecodesCombinedSearchFilters() throws Exception {
        final YoutubeSearchSortFilter filter = new YoutubeSearchSortFilter.Builder()
                .setSortOrder(SortOrder.views)
                .setDateFilter(DateFilter.week)
                .setTypeFilter(TypeFilter.video)
                .setLenFilter(LenFilter.duration_short)
                .addFeature(Features.is_hd)
                .addFeature(Features.subtitles)
                .build();

        final SearchRequest decoded = filter.decodeSp(filter.getSp());

        assertEquals((long) SortOrder.views.getValue(), decoded.sorted);
        assertEquals((long) DateFilter.week.getValue(), decoded.filter.date);
        assertEquals((long) TypeFilter.video.getValue(), decoded.filter.type);
        assertEquals((long) LenFilter.duration_short.getValue(), decoded.filter.length);
        assertTrue(decoded.filter.is_hd);
        assertTrue(decoded.filter.subtitles);
    }
}
