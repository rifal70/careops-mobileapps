package com.digimaster.digicourse.digicyber.apiHelper;

import com.digimaster.digicourse.digicyber.Model.AchievementResponse;
import com.digimaster.digicourse.digicyber.Model.CourseDetailResponse;
import com.digimaster.digicourse.digicyber.Model.CourseListModuleResponse;
import com.digimaster.digicourse.digicyber.Model.CourseResponse;
import com.digimaster.digicourse.digicyber.Model.DataResponse;
import com.digimaster.digicourse.digicyber.Model.DetailCourseResponse;
import com.digimaster.digicourse.digicyber.Model.DetailCourseResponseXXX;
import com.digimaster.digicourse.digicyber.Model.EAssessmentResponse;
import com.digimaster.digicourse.digicyber.Model.FaqResponse;
import com.digimaster.digicourse.digicyber.Model.Faqcategoryresponse;
import com.digimaster.digicourse.digicyber.Model.FinishedTaskResponse;
import com.digimaster.digicourse.digicyber.Model.GroupResponse;
import com.digimaster.digicourse.digicyber.Model.HistoryResponse;
import com.digimaster.digicourse.digicyber.Model.IndoResponse;
import com.digimaster.digicourse.digicyber.Model.InterResponse;
import com.digimaster.digicourse.digicyber.Model.LibraryDetailResponse;
import com.digimaster.digicourse.digicyber.Model.LibraryResponse;
import com.digimaster.digicourse.digicyber.Model.LibraryResponse2;
import com.digimaster.digicourse.digicyber.Model.ListGroupAdminResponse;
import com.digimaster.digicourse.digicyber.Model.ListGroupResponse;
import com.digimaster.digicourse.digicyber.Model.ListMemberAdminResponse;
import com.digimaster.digicourse.digicyber.Model.MemberResponse;
import com.digimaster.digicourse.digicyber.Model.ModuleDetail2temResponse;
import com.digimaster.digicourse.digicyber.Model.ModuleResponse;
import com.digimaster.digicourse.digicyber.Model.NewsResponse;
import com.digimaster.digicourse.digicyber.Model.OnlineResponse;
import com.digimaster.digicourse.digicyber.Model.OnsiteResponse;
import com.digimaster.digicourse.digicyber.Model.PackageResponse;
import com.digimaster.digicourse.digicyber.Model.QuizResponse;
import com.digimaster.digicourse.digicyber.Model.RegisteredEventResponse;
import com.digimaster.digicourse.digicyber.Model.ScheduleResponse;
import com.digimaster.digicourse.digicyber.Model.ScoreResponse;
import com.digimaster.digicourse.digicyber.Model.TaskAssResponse;
import com.digimaster.digicourse.digicyber.Model.TaskLibResponse;
import com.digimaster.digicourse.digicyber.Model.TeacherReqResponse;
import com.digimaster.digicourse.digicyber.Model.TeacherResponse;
import com.digimaster.digicourse.digicyber.Model.TopicResponse;
import com.digimaster.digicourse.digicyber.Model.UsersResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by Teke on 01/11/2017.
 */

public interface BaseApiService {

    @GET("api/apicoursenow")
    Call<LibraryResponse> getLibrary();

    @GET("api/apicourse")
    Call<LibraryResponse2> getLibrary2();
    @FormUrlEncoded
    @POST("api/apicourselistmodule")
    Call<CourseListModuleResponse> getModulDetail(@Field("course_id") String course_id);
    @FormUrlEncoded
    @POST("api/apicourseach")
    Call<LibraryResponse> getLibraryach(@Field("user_id") String user_id);
    @FormUrlEncoded
    @POST("api/apicourselistmoduleach")
    Call<CourseListModuleResponse> getModulDetailAch(@Field("course_id") String course_id,
                                                     @Field("user_id") String user_id);
    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })

    @POST("user/auth/login_sementara_kaya_cinta_ku_padanya")
    Call<ResponseBody> loginRequest(@Field("id_number") String id_number,
                                    @Field("password") String password);


    @FormUrlEncoded
    @POST("api/apicoursetitle")
    Call<ResponseBody> getTitlecourse (@Field("course_id") String course_id);
    @FormUrlEncoded
    @POST("api/apimoduletitle")
    Call<ResponseBody> getTitleModul (@Field("module_id") String course_id);

    @FormUrlEncoded
    @POST("api/apimoduledetail")
    Call<ModuleDetail2temResponse>getmoduldetail2(@Field("module_id")String module_id);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("user/auth/login_as_admin")
    Call<ResponseBody> loginAdminRequest(@Field("id_number") String id_number,
                                    @Field("password") String password);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("api/Resetpass/forgot_password")
    Call<ResponseBody> forgetPass(@Field ("user_email") String email);


    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("user/auth/create_user")
    Call<ResponseBody> registerRequest(@Field("phone") String phone,
                                       @Field("fname") String fname,
                                       @Field("lname") String lname,
                                       @Field("nickname") String nickname,
                                       @Field("password") String password,
                                       @Field("email") String email);

    @Multipart
    @POST("user/auth/uploadPic")
    Call<ResponseBody> registerfoto(
            @Part("uid") RequestBody uid,
            @Part("firstName") RequestBody firstName,
            @Part("lastName") RequestBody lastName,
            @Part("nickname") RequestBody nickname,
            @Part("phone") RequestBody phone,
            @Part MultipartBody.Part image);

    @Multipart
    @POST("quiz/uploadFileActivity")
    Call<ResponseBody> submitFileActivity(
            @Part("uid") RequestBody uid,
//            @Part("firstName") RequestBody firstName,
//            @Part("lastName") RequestBody lastName,
//            @Part("nickname") RequestBody nickname,
//            @Part("phone") RequestBody phone,
            @Part MultipartBody.Part image);

    @GET
    Call<ResponseBody> generatePdfAchieve(@Url String url);


    @FormUrlEncoded
    @POST("onsite/ambilPerUser")
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    Call<DataResponse> requestAmbilPerUsesOnsite(@Field("uid") String uid);

    @FormUrlEncoded
    @POST("onsite/ambilPerUserLib")
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    Call<TaskLibResponse> ambilTaskLib(@Field("uid") String uid);

    @FormUrlEncoded
    @POST("score/get_all_achievement")
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    Call<AchievementResponse> getAchievementById(@Field("uid") String uid,
                                                 @Field("module_id")String module_id);

    @FormUrlEncoded
    @POST("onsite/ambilPerUserAsses")
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    Call<TaskAssResponse> ambilTaskAss(@Field("uid") String uid);

    @GET("home/get_news")
    Call<NewsResponse> getNews();


    @FormUrlEncoded
    @POST("apiari/apisubmitfaq")
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    Call<FaqResponse> getFaq(@Field("teks") String teks);

    @GET("apiari/apikategorifaqari")
    Call<Faqcategoryresponse> getfaqcategory();
    @GET("apiari/apifaqari")
    Call<FaqResponse> getFaq2();
    @FormUrlEncoded
    @POST("apiari/apifilterfaq")
    Call<FaqResponse> getFaq3(@Field("faq_category") String faq_category);

    @GET("home/get_home_banner")
    Call<ResponseBody> getBanners();

    @GET("home/get_online_course")
    Call<OnlineResponse> getOnlineCour();

    @GET("home/get_onsite_course")
    Call<OnsiteResponse> getOnsiteCour();

    @GET("home/get_e_assessment")
    Call<EAssessmentResponse> getAssess();

//    @GET("online/get_all_library")
//    Call<LibraryResponse> getLibrary();

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("online/get_finished_task")
    Call<FinishedTaskResponse> getFinishTask(@Field("user_id") String user_id);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("onsite/get_register_event")
    Call<RegisteredEventResponse> getRegisteredEvent(@Field("user_id") String user_id);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("online/get_all_lib_detail")
    Call<LibraryDetailResponse> getLibDetail(@Field("lib_id") String lib_id);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("home/get_indo_course")
    Call<IndoResponse> getIndoCour(@Field("filter") String filter,
                                   @Field("kelas") String kelas);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("home/get_inter_course")
    Call<InterResponse> getInterCour(@Field("filter") String filter,
                                     @Field("kelas") String kelas);


    @GET
    Call<DetailCourseResponse> getOnlineDetail(@Url String url);

    @GET
    Call<ModuleResponse> getCourseDetail(@Url String url);

    @FormUrlEncoded
    @POST("api/apicoursedetail")
    Call<CourseDetailResponse> getCourseDetail2(@Field("course_id") String course_id);
//Punya ari
    @GET("api/apicoursedetail")
    Call<CourseDetailResponse>getCourseDetailAri();

    @FormUrlEncoded
    @PUT("api/apiupdatepassword")
    Call<ResponseBody> changePass (@Field("user_id") String user_id,
                                   @Field("user_password") String user_password);
    @FormUrlEncoded
    @PUT("api/apiupdatephone")
    Call<ResponseBody> changePhone(@Field("user_id") String user_id,
                                   @Field("user_phone") String user_phone);

    @FormUrlEncoded
    @PUT("api/apiupdate")
    Call<ResponseBody> changeProfile(@Field("user_id") String user_id,
                                     @Field("user_first_name") String user_first_name,
                                     @Field("user_last_name") String user_last_name,
                                     @Field("user_nickname") String user_nickname);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("api/apidelete")
    Call<ResponseBody> deleteAcc(@Field("user_id") String user_id);

    //edited by teke get->post
    @FormUrlEncoded
    @POST("course/get_detail_module")
    Call<TopicResponse> getModuleDetail(@Field("course_id") String course_id,
                                         @Field("module_id") String module_id,
                                         @Field("user_id") String user_id);
    //edited by teke get->post
    @FormUrlEncoded
    @POST("course/get_detail_module")
    Call<TopicResponse> getModuleDetail2(
                                        @Field("module_id") String module_id,
                                        @Field("user_id") String user_id);
    @GET
    Call<DetailCourseResponseXXX> getOnlineDetailXXX(@Url String url);
    //@Query("") String param

    @GET
    Call<ResponseBody> getOnsiteDetail(@Url String url);
    //@Query("") String param

    @GET
    Call<GroupResponse> getGroup(@Url String phone);
    //@Query("") String param

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("quiz/get_quiz_course")
    Call<QuizResponse>  getAllQuiz(@Field("topic_id") String topic_id,
                                   @Field("action_id") String action_id);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("quiz/checkpoint")
    Call<ResponseBody> saveCheckPoint(@Field("user_id") String user_id,
                                      @Field("cour_id") String cour_id,
                                      @Field("material_id") String material_id,
                                      @Field("last_pos") String last_pos,
                                      @Field("last_score") String last_sco
    );

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("quiz/checkpoint_status")
    Call<ResponseBody> getCheckPoint(@Field("user_id") String user_id,
                                     @Field("material_id") String material_id);

    /* END QUIZ BERES */

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("quiz/submit_ans")
    Call<ResponseBody> submitAnswer(@Field("uid") String uid,
                                    @Field("quiz_id") String quiz_id,
                                    @Field("user_answer1") String user_answer1,
                                    @Field("user_answer2") String user_answer2,
                                    @Field("score") String score
    );

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("quiz/get_score_user")
    Call<ScoreResponse> getScoreUser(@Field("user_id") String user_id);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("quiz/submit_score")
    Call<ResponseBody> submitScore(@Field("uid") String uid,
                                   @Field("material_id") String material_id,
                                   @Field("course_id") String course_id,
                                   @Field("score") String score,
                                   @Field("waktu_mulai") String waktu_mulai,
                                   @Field("waktu_akhir") String waktu_akhir,
                                   @Field("status") String status);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("quiz/get_assess_status")
    Call<ResponseBody> getQuizStat(@Field("user_id") String user_id,
                                   @Field("material_id") String material_id);

    //THIS CODE IS FULL OF LOVE BEWARE
    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("online/love")
    Call<ResponseBody> loveMe(@Field("uid") String uid,
                              @Field("cour_id") String cour_id);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("online/unlove")
    Call<ResponseBody> hateMe(@Field("uid") String uid,
                              @Field("cour_id") String cour_id);


    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("online/get_love_status")
    Call<ResponseBody> getLoveStat(@Field("user_id") String user_id,
                                   @Field("course_id") String course_id);


    //BATAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAS

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("user/auth/logout")
    Call<ResponseBody> requestLogout(@Field("email") String email);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("onsite/regis_onsite")
    Call<ResponseBody> requestRegisOnsite(@Field("uid") String uid,
                                          @Field("event_id") String event_id,
                                          @Field("event_name") String event_name
                                          );

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("onsite/finish_task")
    Call<ResponseBody> submitTask         (@Field("uid") String uid,
                                           @Field("task_id") String task_id,
                                           @Field("sub_task_id") String sub_task_id,
                                           @Field("task_name") String task_name,
                                           @Field("feedback") String feedback);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("score/finish_progress")
    Call<ResponseBody> submit_progress     (@Field("uid") String uid,
                                           @Field("course_id") String course_id,
                                           @Field("module_id") String module_id,
                                           @Field("topic_id") String topic_id,
                                           @Field("action_id") String action_id,
                                           @Field("answer") String answer);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("onsite/finish_task_lib")
    Call<ResponseBody> submitTaskLib         (@Field("uid") String uid,
                                           @Field("task_id") String task_id,
                                           @Field("sub_task_id") String sub_task_id,
                                           @Field("task_name") String task_name,
                                           @Field("feedback") String feedback);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("online/regis_online")
    Call<ResponseBody> requestRegisOnline(@Field("uid") String uid,
                                          @Field("nama_rek") String nama_rek,
                                          @Field("cour_id") String cour_id,
                                          @Field("status") String status,
                                          @Field("total") String total,
                                          @Field("slug") String slug);

    //TEACHETEACHERTEACHERTEACHERTEACHERTEACHERTEACHERTERACHETERACHERTEACHERTEACHERTEACHERTEACHERTEACHER
    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("teacher/get_teacher")
    Call<TeacherResponse> getTeacher(@Field("pengalaman") String pengalaman);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("teacher/requestTeach")
    Call<ResponseBody> requestTeacher(@Field("uid") String uid,
                                      @Field("teach_id") String teach_id,
                                      @Field("name") String name,
                                      @Field("schedule_id") String schedule_id,
                                      @Field("date") String date,
                                      @Field("time") String time,
                                      @Field("stat") String stat
    );

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("teacher/acceptRequestTeach")
    Call<ResponseBody> acceptRequestTeacher(@Field("uid") String uid);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("onsite/joinGroup")
    Call<ResponseBody> joinGroup(@Field("uid") String uid,
                                 @Field("gid") String gid);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("teacher/declineRequestTeach")
    Call<ResponseBody> declineRequestTeacher(@Field("uid") String uid);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("teacher/get_teacher_schedule")
    Call<ScheduleResponse> getTeacherSchedule(@Field("user_id") String user_id);


    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("teacher/get_history_request")
    Call<HistoryResponse> getHistory(@Field("uid") String uid);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("teacher/get_request_teach")
    Call<TeacherReqResponse> getReqTeach(@Field("uid") String uid);


    //IMPORTAAAAAAAAAAAAAAAAAAAAAAAAAN SERTIFIKED
    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("cert/cert_pdf")
    Call<ResponseBody> submitCert(@Field("uid") String uid,
                                  @Field("name") String name,
                                  @Field("course_id") String course_id);


    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("home/get_level")
    Call<ResponseBody> getLevel(@Field("uid") String uid);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("teacher/get_teacher_package")
    Call<PackageResponse> getPackage(@Field("user_id") String user_id);

    @GET("user/auth/get_user")
    Call<UsersResponse> getUser();

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("user/auth/get_all_member")
    Call<MemberResponse> getAllMember(@Field("admin_id") String admin_id);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("user/auth/get_all_group")
    Call<ListGroupResponse> getListGroup(@Field("user_id") String user_id);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("user/auth/get_all_group_admin")
    Call<ListGroupAdminResponse> getListGroupAdmin(@Field("admin_id") String admin_id);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("user/auth/get_all_member_admin")
    Call<ListMemberAdminResponse> getListMemberAdmin(@Field("group_id") String group_id);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("online/assign_task")
    Call<ResponseBody> assignTask(@Field("user_id") String user_id,
                                  @Field("group_id") String group_id,
                                  @Field("admin_id") String admin_id,
                                  @Field("task_id") String task_id,
                                  @Field("task_type") String task_type);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("onsite/get_feedback_user")
    Call<ResponseBody> getFeedback(@Field("user_id") String user_id,
                                       @Field("cour_id") String cour_id,
                                       @Field("material_id") String material_id);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("group/create_group")
    Call<ResponseBody> createGroup(@Field("guid") String guid,
                                          @Field("group_name") String group_name);

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Client-Service: frontend-client"
    })
    @POST("course/get_assigned_course_byId")
    Call<CourseResponse> getCourseById(@Field("uid") String uid);
}
