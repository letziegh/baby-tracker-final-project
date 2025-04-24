package com.coderscampus.babytracker.controller;

import com.coderscampus.babytracker.domain.Parent;
import com.coderscampus.babytracker.service.ParentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
public class HomeController {

    private final ParentService parentService;

    public HomeController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout";
    }
    

    @GetMapping("/dashboard")
    public String dashboardPage(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return "redirect:/";
        }

        String email = null;
        String name = null;
        
        // Try to get email from different possible attributes
        if (principal.getAttribute("email") != null) {
            email = principal.getAttribute("email");
        } else if (principal.getAttribute("login") != null) {
            // GitHub case
            email = principal.getAttribute("login") + "@github.com";
        }
        
        // Try to get name from different possible attributes
        if (principal.getAttribute("name") != null) {
            name = principal.getAttribute("name");
        } else if (principal.getAttribute("login") != null) {
            name = principal.getAttribute("login");
        }

        if (email == null) {
            // If we still don't have an email, use the principal name
            email = principal.getName();
        }

        // Debug information
        System.out.println("Principal attributes: " + principal.getAttributes());
        System.out.println("Principal name: " + principal.getName());
        System.out.println("Email: " + email);
        System.out.println("Name: " + name);

        Parent parent = parentService.findByEmail(email);
        if (parent == null) {
            // Create a new parent if not found
            parent = parentService.saveParent(email, name);
        }

        model.addAttribute("parent", parent);
        return "dashboard";
    }
}

//CODE BELOW IS WHAT I HADE BEFORE AND CODE ABOVE IS WHAT I HAD TO CHANGE TO GET IT TO WORK
// @Controller
// public class HomeController {

//     private final ParentService parentService;

//     public HomeController(ParentService parentService) {
//         this.parentService = parentService;
//     }

//     @GetMapping("/")
//     public String loginPage() {
//         return "login";
//     }

//     @GetMapping("/dashboard")
//     public String dashboardPage(Model model, @AuthenticationPrincipal OAuth2User principal) {
//         if (principal == null) {
//             return "redirect:/login";
//         }

//         String email = principal.getAttribute("email");
//         if (email == null) {
//             // Handle GitHub case where email might not be in the principal
//             email = principal.getAttribute("login") + "@github.com";
//         }

//         Parent parent = parentService.findByEmail(email);
//         if (parent == null) {
//             // This shouldn't happen if CustomOAuth2UserService is working correctly
//             return "redirect:/login";
//         }

//         model.addAttribute("parent", parent);
//         return "dashboard";
//     }
// }

//     @GetMapping("/dashboard")
//     public String dashboardPage(Model model, @AuthenticationPrincipal OAuth2User principal) {
//         if (principal != null) {
//             String email = principal.getAttribute("email");
//             Parent parent = parentService.findByEmail(email);
//             if (parent != null) {
//                 model.addAttribute("parent", parent); // Use Parent from database if found
//             } else {
//                 // Parent not found in database, create a temporary Parent object
//                 // using information from OAuth2User principal
//                 String nameFromOAuth2 = principal.getAttribute("name");
//                 String emailFromOAuth2 = principal.getAttribute("email");

//                 // Create a Parent object with data from OAuth2User (but not saved to DB yet)
//                 Parent tempParent = new Parent();
//                 tempParent.setName(nameFromOAuth2 != null ? nameFromOAuth2 : "User from Google/GitHub"); // Default name if name is null
//                 tempParent.setEmail(emailFromOAuth2 != null ? emailFromOAuth2 : "Email not available"); // Indicate if email is missing

//                 model.addAttribute("parent", tempParent); // Pass this temporary Parent to the view
//             }
//         } else {
//             // Handle case where user is not authenticated (principal is null)
//             return "login";
//         }
//         return "dashboard";
//     }
// }
//@Controller
//public class HomeController {
//
//    private final ParentService parentService;
//
//    public HomeController(ParentService parentService) {
//        this.parentService = parentService;
//    }
//
//    @GetMapping("/")
//    public String loginPage() {
//        return "login";
//    }
//
//    @GetMapping("/dashboard")
//    public String dashboardPage(Model model, @AuthenticationPrincipal OAuth2User principal) {
//        if (principal != null) {
//            String email = principal.getAttribute("email");
//            Parent parent = parentService.findByEmail(email);
//            if (parent != null) {
//                model.addAttribute("parent", parent); // Ensure 'parent' is set in the model
//            } else {
//                model.addAttribute("parent", new Parent("Unknown", "Unknown")); // Avoid null
//            }
//        }
//        return "dashboard";
//    }
//}
//    @GetMapping("/dashboard")
//    public String dashboardPage(Model model, @AuthenticationPrincipal OAuth2User principal) {
//        if (principal != null) {
//            String email = principal.getAttribute("email");
//
//            if (email != null) { // Ensure email is not null before querying
//                // Retrieve parent from database using email
//                Parent parent = parentService.findByEmail(email);
//
//                if (parent != null) {
//                    model.addAttribute("parent", parent); // Add Parent object to the model
//                } else {
//                    // Handle case where parent is not found for the given email
//                    // This might happen if there's an issue in user creation during OAuth2 flow
//                    // You might want to log this or redirect to an error page.
//                    model.addAttribute("error", "Parent user not found."); // Add error message to model
//                    return "error"; // Create an "error.html" page to display errors
//                }
//            } else {
//                // Handle case where email is not available from principal
//                model.addAttribute("error", "Email not found in authentication information.");
//                return "error"; // Redirect to error page
//            }
//        } else {
//            // Handle case where user is not authenticated (principal is null)
//            return "login"; // Redirect unauthenticated users to login page
//        }
//        return "dashboard"; // Return dashboard view if parent is found and added to model
//    }
//}

    //@Controller
//public class HomeController {
//
//    private final ParentService parentService;
//
//    public HomeController(ParentService parentService) {
//        this.parentService = parentService;
//    }
//
//    @GetMapping("/")
//    public String loginPage() {
//        return "login";
//    }
//
//    @GetMapping
//    public Map<String,Object> currentUser (OAuth2AuthenticationToken oAuth2AuthenticationToken){
//        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
//    }
//
//    @GetMapping("/dashboard")
//    public String dashboardPage(Model model, @AuthenticationPrincipal OAuth2User principal) {
//        if (principal != null) {
//            String email = principal.getAttribute("email");
//
//            // Retrieve parent from database
//            Parent parent = parentService.findByEmail(email);
//            model.addAttribute("parent", parent);
//        }
//        return "dashboard";
//    }
//    public String dashboardPage(Model model, @AuthenticationPrincipal OAuth2User principal) {
//        if (principal != null) {
//            String email = principal.getAttribute("email");
//            Parent parent = parentService.findByEmail(email);
//            if (parent != null) {
//                model.addAttribute("parent", parent); // Ensure 'parent' is set in the model
//            } else {
//                model.addAttribute("parent", new Parent("Unknown", "Unknown")); // Avoid null
//            }
//        }
//        return "dashboard";
//    }
//}
    //old starter code below
//    @GetMapping("/")
//    public String home() {
//        return "Thank God you made it to the home page!";
//    }
//
//    @GetMapping("/secured")
//    public String secured() {
//        return "Hello, you are secured!";
//    }
//}




