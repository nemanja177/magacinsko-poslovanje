import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PreduzeceComponent } from '../list/preduzece.component';
import { PreduzeceDetailComponent } from '../detail/preduzece-detail.component';
import { PreduzeceUpdateComponent } from '../update/preduzece-update.component';
import { PreduzeceRoutingResolveService } from './preduzece-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const preduzeceRoute: Routes = [
  {
    path: '',
    component: PreduzeceComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PreduzeceDetailComponent,
    resolve: {
      preduzece: PreduzeceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PreduzeceUpdateComponent,
    resolve: {
      preduzece: PreduzeceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PreduzeceUpdateComponent,
    resolve: {
      preduzece: PreduzeceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(preduzeceRoute)],
  exports: [RouterModule],
})
export class PreduzeceRoutingModule {}
