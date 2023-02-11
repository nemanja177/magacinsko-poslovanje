import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { JedinicaMereComponent } from '../list/jedinica-mere.component';
import { JedinicaMereDetailComponent } from '../detail/jedinica-mere-detail.component';
import { JedinicaMereUpdateComponent } from '../update/jedinica-mere-update.component';
import { JedinicaMereRoutingResolveService } from './jedinica-mere-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const jedinicaMereRoute: Routes = [
  {
    path: '',
    component: JedinicaMereComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JedinicaMereDetailComponent,
    resolve: {
      jedinicaMere: JedinicaMereRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JedinicaMereUpdateComponent,
    resolve: {
      jedinicaMere: JedinicaMereRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JedinicaMereUpdateComponent,
    resolve: {
      jedinicaMere: JedinicaMereRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(jedinicaMereRoute)],
  exports: [RouterModule],
})
export class JedinicaMereRoutingModule {}
