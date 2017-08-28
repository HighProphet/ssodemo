package pw.highprophet.ssocenter.test;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pw.highprophet.ssocenter.web.controller.SingleSignOnController;

/**
 * Created by HighProphet945 on 2017/8/3.
 */
public class SSOTest {

    @org.junit.Test
    public void testFetch() throws Exception {
        SingleSignOnController controller = new SingleSignOnController();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/pw/highprophet/ssocenter/fetch"))
                .andExpect(MockMvcResultMatchers.content().string("sdfsdf"));
    }
}
