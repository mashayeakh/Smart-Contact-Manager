package com.scm.Controller;

import com.scm.Entities.Contact;
import com.scm.Entities.RegisteredUser;
import com.scm.Repository.ContactRepository;
import com.scm.Repository.UserRepository;
import com.scm.Service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class WelcomeController {

    //injecting constructor based parameter
    private UserRepository userRepository;
    private ContactRepository contactRepository;
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WelcomeController(UserRepository userRepository,
                             ContactRepository contactRepository,
                             UserService userService,
                            BCryptPasswordEncoder bCryptPasswordEncoder ) {

        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.userService = userService;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @ModelAttribute
    public void commonThings(Model model, Principal principal) {
        //fetching email.
        String _fetchingUserName = principal.getName();
        System.out.println(_fetchingUserName);

        //fetching single row of a logged-in user
        System.out.println(this.userRepository.findByEmail(_fetchingUserName));

        model.addAttribute("user", this.userRepository.findByEmail(_fetchingUserName));

    }

    @GetMapping("/welcome-user")
    public String userHome(Model model, Principal principal) {

        model.addAttribute("title", "user-Dashboard");
        return "/pages/user/welcomeUserPage";
    }

    //showing add contact form
    @GetMapping("/add_contact")
    public String addContact(Model model, Principal principal) {

        model.addAttribute("title", "Add-Contact");
        model.addAttribute("contact", new Contact()); // Add the contact object to the model
        return "pages/user/add_contact";
    }

    //processing add contact form
//processing add contact form
    @PostMapping("/process-contact")
    public String processContact(Model model,
                                 @Valid @ModelAttribute Contact contact,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal,
                                 @RequestParam("profileImage") MultipartFile file) {

        try {
            if (result.hasErrors()) {
                model.addAttribute("contact", contact);
                return "pages/user/add_contact";
            }

            //processing and uploading file
            if (file.isEmpty()) {
                System.out.println("File is empty");

            } else {
                //file is ready to be uploaded
                //setting the name of the img
                contact.setImageUrl(file.getOriginalFilename());

                //telling them the path where to save the file
                File saveFile = new ClassPathResource("static/images").getFile();


                Path path = Paths.get(saveFile.getAbsolutePath() +
                        File.separator + file.getOriginalFilename());

                System.out.println(path);

                //copying file- 3 things to be passed. input stream, path and
                // if same photo is existed, then just replace the photo
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);


            }


            // Get the email of the currently logged-in user
            String _loggedInEmail = principal.getName();

            //getting the info about the logged in persone
            RegisteredUser _fetchingAll_InfoAbout_LoggedInUser = this.userRepository.findByEmail(_loggedInEmail);


            //adding the fetched user with the contact
            contact.setRUser(_fetchingAll_InfoAbout_LoggedInUser);

            // Save the contact
            userService.saveContact(contact);

            redirectAttributes.addFlashAttribute("success", true);

        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            //detailed exception in console.
            e.printStackTrace();
        }
        return "redirect:/user/add_contact";
    }

    //    http://localhost:1008/user/view-contacts/2
    @GetMapping("/view-contacts/{nameOfPage}")
    public String viewContacts(@PathVariable("nameOfPage") Integer nameOfPage,
                               Model model, Principal principal) {


        //pagination:
        //per page- 2 rows
        //current page - 0 [current page means page]
        //which page you are in currently - nameOfPage.


        String _fetchingEmail = principal.getName();

        //full history of that particular _fetching email
        RegisteredUser _gettingUser = this.userRepository.findByEmail(_fetchingEmail);

        //fetching user id who is logged in.
//         this.contactRepository.findContactBy_RegisteredUser(_gettingUser.getId());


        //pageable and sorting
        Pageable pageable = PageRequest.of(nameOfPage, 3);


        Page<Contact> contacts = this.contactRepository.findContactBy_RegisteredUser(_gettingUser.getId(), pageable);


        System.out.println("total rows:  " + contacts.getTotalElements());


        model.addAttribute("title", "View Contact");

        model.addAttribute("all_Contact", contacts);

        //2 things in pageable

        //current page
        model.addAttribute("currentPage", nameOfPage);
        //no of pages after implementation
        model.addAttribute("totalPage", contacts.getTotalPages());
        //total no of contacts
        model.addAttribute("total_contacts", contacts.getTotalElements());

        return "pages/user/show_contacts";
    }

    //delete contact by id
    @GetMapping("/delete/{customId}/{currentPage}")
    public String deleteContact(@PathVariable("customId") String customId,
                                @PathVariable("currentPage") int currentPage) {

        // Extract the main ID from the custom ID
        long contactId = extractId_FromPrefix(customId);

        // Delete the user by ID
        this.userService.deleteUserById(contactId);

        // Redirect the user back to the current page
        return "redirect:/user/view-contacts/" + currentPage;
    }


    //    since my id looks like SCM2020503 and my actual id is 503, so I must extract the id.
    public long extractId_FromPrefix(String customId) {

        //converting the prefix into empty string
        String prefix = "SCM2020";
        String mainID = customId.replace(prefix, "");

        //main id is string but i need long
        return Long.parseLong(mainID);

    }


    //for showing from with correct value
    @PostMapping("/update-contact/{id}")
    public String updateContact(Model model,
                                @PathVariable("id")long id){
        //title
        model.addAttribute("title","update-contact");

        //when anything needs to be updated, in normal case, the values should be filled with the actual values.
        //to make this happen, we need to find the row through id of that contact

       Contact _fetchingSingleRow = this.contactRepository.findById(id).get();

       //sending this contact to the view using model
        model.addAttribute("contact",_fetchingSingleRow);

        return "pages/user/update_contact";
    }

    //updating code
   @PostMapping("/updating-process")
    public String updateHandler(@ModelAttribute("contact") Contact contact,
                                Principal principal) {

       System.out.println("Name " + contact.getName());
       System.out.println("Email " + contact.getEmail());
       System.out.println("id" + contact.getContactId());

       //getting user id;
       RegisteredUser user = this.userRepository.findByEmail(principal.getName());

       //now setting the user with contact
       contact.setRUser(user);

       System.out.println("Logged in user : " + user);
       System.out.println("logged user's id : " + user.getId());


       this.userService.updateUser(contact);

       return "redirect:/user/view-contacts/0";

   }

//   Logged in user's profile
    @GetMapping("/profile")
    public String loggedIn_Profile(Model model, Principal principal){

       String name =  principal.getName();



        RegisteredUser _fullRow_Of_LoggedUser =  userRepository.findByEmail(name);

        System.out.println(_fullRow_Of_LoggedUser);


        model.addAttribute("title","Profile");
        model.addAttribute("all_info", _fullRow_Of_LoggedUser);
        model.addAttribute("enable", _fullRow_Of_LoggedUser.isEnabled());

        return "pages/user/profile";
    }


    //change password
    @GetMapping("/settings")
    public String settings(Model model){
        model.addAttribute("title","settings");
        return "pages/user/settings";
    }

    //handler for requesting "change password"
    @PostMapping("/change-password")
//    using request param to get the old and new password from Form [we used name, See that]
    public String changePassword(@RequestParam("oldPassword")String oldPassword,
                                 @RequestParam("newPassword")String newPassword,
                                 Principal principal,
                                 HttpSession session){

        //lets check whether the values get printed or not.
        System.out.println("Old Password: "+oldPassword);
        System.out.println("New Password: "+newPassword);

        //getting the current password of that logged in user.
        String logged_InUser_Mail =   principal.getName();
        System.out.println("logged in user: "+logged_InUser_Mail);

      RegisteredUser loggedIn_Row =  this.userRepository.findByEmail(logged_InUser_Mail);
        System.out.println("Password:  "+loggedIn_Row.getPassword());

        //lets match with old password with the current password that user has.
        if(this.bCryptPasswordEncoder.matches(oldPassword,loggedIn_Row.getPassword())){
            //now change the password
            loggedIn_Row.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
//            this.userService.saveUser(loggedIn_Row);
            this.userRepository.save(loggedIn_Row);
        }else{
            return "redirect:/user/settings";
        }
        return  "redirect:/user/welcome-user";


    }


}
