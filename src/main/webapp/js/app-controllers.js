//Define the Controllers module
var controllers = angular.module('myAppControllers', []);

controllers.controller('PersonListController', ['Restangular', '$scope',
    function(Restangular, $scope) {

        var resource = Restangular.all('persons/');
        resource.getList().then(function(persons) {
            $scope.persons = persons;
        });

        
    }]);

controllers.controller('PersonCreateController', ['Restangular', '$scope', '$sce', 
    function(Restangular, $scope, $sce) {  
        
        //Initialise Person
        $scope.person = {name:"place", surname: "holder", age: 0};       
        
        //Initialise Messages
        $scope.showSuccess = false;
        $scope.showFail = false;
        $scope.errorMsg = "";
                
        //Submit Action
        $scope.newPerson = function() {
            
            //validate Person
            if(validatePerson()){
            
            Restangular.all('persons/new').post({ name: "Test", surname: "Test", age: 12 }, {type: "Person"}).then(
                    function(result) {
                        console.log(result);
                        if(result=="ok"){
                            $scope.showSuccess = true;
                        }
                    });
                }
                
            }
            
          function validatePerson(){
              var valid = true;
              var errors = "";
              if($scope.person.name.length<2){  
                  errors += "<strong>Name:</strong> must me longer than 2 chars.<br>";                  
                  valid = false;
              }
              if($scope.person.age<18){  
                   errors += "<strong>Age:</strong> must me be at least 18.<br>";
                  valid = false;
              }
              
              $scope.errorMsg = $sce.trustAsHtml(errors);
              if(valid){$scope.showFail = false;}else{$scope.showFail = true;}
              return valid;
          }

    }]);

controllers.controller('PersonDetailController', ['Restangular', '$scope', '$routeParams',
    function(Restangular, $scope, $routeParams) {
        
        $scope.ref = $routeParams.personId;
        
        //get person by id
        var resource = Restangular.one('persons/', $routeParams.personId);
        resource.get().then(function(person) {
            $scope.person = person;
        });
        
    }]);